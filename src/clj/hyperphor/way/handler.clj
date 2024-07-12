(ns hyperphor.way.handler
  (:require [compojure.core :refer [defroutes context GET POST make-route routes]]
            [compojure.route :as route]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.format-response :refer [wrap-restful-response]]
            [ring.middleware.basic-authentication :refer [wrap-basic-authentication]]
            [org.candelbio.multitool.core :as u]
            [org.candelbio.multitool.cljcore :as ju]
            [hyperphor.way.oauth :as oauth]
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

(defn login-view
  []
  (html/html-frame
   {:page :login}                       ;TODO this has changed
   "Login"
   [:div.black
    [:div.login-panel.p-4
     [:table
      [:tr
       [:td
        [:h4 "Welcome to RawSugar, PICI's raw data handling tool."]] ;TODO config
       [:td
        [:div {:style (html/style-arg {:margin-left "60px"}) }
         [:a {:href "/oauth2/google"}
          [:img {:src "/img/google-signin.png"}]]]]]]]]))

(defroutes base-site-routes
  (GET "/" [] (spa))                    ;index handled by spa
  (GET "/login" [] (login-view)) ;TODO only if OAuth configured
  (GET "/admin" req (admin/view req))
  #_ (GET "*" [] (spa))                    ;default is handled by spa
  (route/not-found "Not found")
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

(defn site-routes
  [app-site-routes]
  (-> (routes app-site-routes base-site-routes)
      (wrap-restful-response)

      (oauth/wrap-oauth)

      ;; TODO isn't this redundant with middleware-site-defaults?
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

;;; TODO should be in .cljc, should be parameterizable
(def api-base "/api")

(defroutes base-api-routes  
  (context api-base []
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

    #_
    (route/not-found (content-response {:error "Not Found"}))
    ))

(defn api-routes
  [app-api-routes]
  (-> (routes app-api-routes base-api-routes)
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
  [app-site-routes app-api-routes]
  (let [base (routes (api-routes app-api-routes) (site-routes app-site-routes))]
    (if (and (config/config :basic-auth) ;TODO just use :basic-auth-creds and eliminate this extra var
             (not (config/config :dev-mode)))
      (wrap-basic-authentication base authenticated?)
      base)))
