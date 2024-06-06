(ns hyperphor.way.handler
  (:require [compojure.core :refer [defroutes context GET POST make-route routes]]
            [compojure.route :as route]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.format-response :refer [wrap-restful-response]]
            [ring.middleware.basic-authentication :refer [wrap-basic-authentication]]
            [org.candelbio.multitool.core :as u]
            [org.candelbio.multitool.cljcore :as ju]
            [hyperphor.way.views.html :as html]
            [hyperphor.way.views.admin :as admin]
            [hyperphor.way.data :as data]
            [hyperphor.way.config :as config]
            [ring.logger :as logger]
            [ring.middleware.session.memory :as ring-memory]
            [ring.middleware.resource :as resource]
            [taoensso.timbre :as log]
            [ring.middleware.defaults :as middleware]
            [ring.util.response :as response]
            [clojure.string :as str]
            [environ.core :as env]
            )
  (:use [hiccup.core])
  )

;;; Ensure API and site pages use the same store, so authentication works for API.
(defonce common-store (ring-memory/memory-store))

(defn authenticated?
  [name pass]
  (= [name pass] (config/config :basic-auth-creds)))

(defn content-response
  [data & [status]]
  ;; Try to return vectors for consistency. list? is false for lazy seq. Note doesn't do anything about internal lists.
  (let [data (if (and (sequential? data) (not (vector? data)))
               (into [] data)
               data)]
    {:status (or status 200)
     :headers {}
     ;; Warning: this breaks the file-upload response because it isn't under wrapper
     :body data}))

(defn spa
  []
  (response/content-type
   (content-response
    (html/html-frame-spa))
   "text/html"))

(defroutes site-routes
  (GET "/" [] (spa))                    ;index handled by spa
  (GET "/admin" req (admin/view req))
  (GET "*" [] (spa))                    ;default is handled by spa
  (route/not-found "Not found")         ;TODO this  will never be reached? But spa should do something reasonable with bad URLs
  )

;;; Must be something built-in for this?
(defn wrap-filter
  [handler path]
  (make-route nil path handler))

;;; Weird that this isn't a standard part of ring
(defn wrap-no-read-eval
  [handler]
  (fn [request]
    (binding [*read-eval* false]
      (handler request))))

;;; Weird that this isn't a standard part of ring
(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch clojure.lang.ExceptionInfo e
        {:status 400 :headers {} :body (str "Error: " (ex-message e))})
      (catch Throwable e
        {:status 500 :headers {} :body (print-str e)}))))

(defn wrap-api-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch clojure.lang.ExceptionInfo e
        (log/error "API error" (pr-str e))
        {:status 400 :headers {} :body {:error (ex-message e) :data (ex-data e)}})
      (catch Throwable e
        (log/error "API error" (pr-str e))
        {:status 500 :headers {} :body (print-str e)}))))

(def middleware-site-defaults
  "A default configuration for a browser-accessible website, based on current
  best practice."
  {:params    {:urlencoded true
               :multipart  true
               :nested     true
               :keywordize true}
   :cookies   true
   :session   {:flash true
               :cookie-attrs {:http-only true}
               :store common-store}
   :security  {:anti-forgery   true
               :frame-options  :sameorigin
               :content-type-options :nosniff}
   :static    {:resources "public"}
   :responses {:not-modified-responses true
               :absolute-redirects     false
               :content-types          true
               :default-charset        "utf-8"}})


(def site-defaults
  (-> middleware-site-defaults                   ;was middleware/site-defaults
      (assoc-in [:security :anti-forgery] false)          ;necessary for upload (TODO not great from sec viewpoint)
      (assoc :cookies true)
      (assoc-in [:session :cookie-attrs :same-site] :lax) ;for oauth
      (assoc-in [:session :store] common-store)))

(def site
  (-> site-routes
      (wrap-restful-response)
      (resource/wrap-resource "public" {:allow-symlinks? true}) ;allow symlinks in static dir
      (middleware/wrap-defaults site-defaults)                                  ;TODO turn off static thing in here
      wrap-no-read-eval
      wrap-exception-handling
      (logger/wrap-with-logger          ;hook Ring logger to Timbre
       {:log-fn (fn [{:keys [level throwable message]}]
                  (log/log level throwable message))})
      ))

;;; Copied oout of middleware
(def middleware-api-defaults
  "A default configuration for a HTTP API."
  {:params    {:urlencoded true
               :keywordize true}
   :responses {:not-modified-responses true
               :absolute-redirects     false
               :content-types          true
               :default-charset        "utf-8"}})

(def api-defaults
  (-> middleware-api-defaults                      ;was middlewar/api-defaults
      (assoc :cookies true)
      (assoc-in [:session :flash] false)
      (assoc-in [:session :cookie-attrs] {:http-only true, :same-site :lax})
      (assoc-in [:session :store] common-store)))

(defroutes api-routes  
  (context "/api/v2" []
    (GET "/config" _                    ;TODO try to build config into compiled js and eliminate this
      (content-response (config/config)))
    (GET "/data" req                    ;params include data-id and other
      (content-response (data/data (:params req))))
    #_                                  ;TODO dev-mode only
    (GET "/error" req                   ;For testing error reporting
      (content-response (/ 0 0)))
    #_
    (POST "/error" req                   ;For testing error reporting
      (content-response (/ 0 0)))

    (route/not-found (content-response {:error "Not Found"}))
    ))

(def rest-api
  (-> api-routes
      (middleware/wrap-defaults api-defaults)
      wrap-no-read-eval
      wrap-api-exception-handling
      (logger/wrap-with-logger          ;hook Ring logger to Timbre
       {:log-fn (fn [{:keys [level throwable message]}]
                  (log/log level throwable message))})
      (wrap-restful-format)
      (wrap-filter "/api/*")            ;filter early so edn responses don't go to regular site requests
      ))

(defn app
  []
  (let [base (routes rest-api site)]
    (if (and (config/config :basic-auth)
             (not (config/config :dev-mode)))
      (wrap-basic-authentication base authenticated?)
      base)))


