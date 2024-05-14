(ns hyperphor.way.views.admin
  (:require [environ.core :as env]
            [hyperphor.way.views.html :as html]
            [hiccup.util :as hu]
            )
  )

;;; TODO gaping security hole, not really acceptable. At least it is behind basic-auth
;;; Maybe automatically redact items with "crendential" or other privacy relevant strings


;;; TODO git commit information, etc. Can't run git on server, somehow need to sneak it into an uberjar. Hm you could just
;;; link a resource to  ./.git/logs/HEAD

(defn map-table
  [name map]
  [:div [:h2 name]
   [:table.table-bordered
    (for [key (sort (keys map))]
      [:tr
       [:th (str key)]
       [:td (if (map? (get map key))
              (map-table "" (get map key))
              (hu/escape-html (str (get map key))))]])]])

(defn view
  [req]
  (html/html-frame
   {}
   "Admin"
   [:div
    (map-table "Env" env/env)
    (map-table "System/getenv" (System/getenv))
    (map-table "HTTP req" req)
    ]))
