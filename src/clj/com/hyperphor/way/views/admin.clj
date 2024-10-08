(ns com.hyperphor.way.views.admin
  (:require [environ.core :as env]
            [com.hyperphor.way.views.html :as html]
            [com.hyperphor.way.config :as config]
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
  (if (config/config :dev-mode)
    (html/html-frame
     {}
     "Admin"
     [:div
      (map-table "Way Config" (config/config))
      (map-table "Env" env/env)
      (map-table "System/getenv" (System/getenv))
      (map-table "HTTP req" req)
      ])
    (throw (ex-info "Dev mode only" {:req req}))))
