(ns hyperphor.way.demo.handler
  (:require [compojure.core :refer [defroutes context GET POST make-route routes]]
            [ring.util.response :as response]
            [hyperphor.way.handler :as wh]
            [hyperphor.way.views.html :as html]
            )
  (:use [hiccup.core])
  )

;;; Standin sample view
(defn country-view
  [id]
  (response/content-type
   (wh/content-response
    (html/html-frame
     {} (str "Country " id)
     [:h3 "Your guide to scenic " id]
     ))
   "text/html"))

;;; TODO re-integrate
(defroutes site-routes
  (GET "/country/:id" [id] (country-view id) )
  )

(def app (wh/app site-routes))

