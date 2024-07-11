(ns hyperphor.way.demo.handler
  (:require [compojure.core :refer [defroutes context GET POST make-route routes]]
            [ring.util.response :as response]
            [hyperphor.way.demo.dbpedia :as dbpedia]
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
     [:div
      [:h3 "Your guide to scenic " id]
      (dbpedia/entity-content id)
      ]))
   "text/html"))

(defn dbpedia-view
  [id]
  (response/content-type
   (wh/content-response
    (html/html-frame
     {} (str "DBPedia on " id)
     [:div
      (dbpedia/entity-content id)
      ]))
   "text/html"))

(defroutes site-routes
  (GET "/country/:id" [id] (country-view id) )
  (GET "/dbpedia/:id" [id] (dbpedia-view id) )
  )

;;; Warning. Do not use "(def app ...)", config isn't necessarily right at compile time
(defn app
  []
  (wh/app site-routes (routes)))

