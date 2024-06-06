(ns hyperphor.way.ui.config
  (:require [hyperphor.way.api :as api]))

(def the-config (atom nil))

(defn init
  [cont]
  (api/ajax-get "/api/v2/config" {:handler #(do (reset! the-config %)
                                                (cont %))}))

(defn config
  [& atts]
  (assert @the-config "Config referenced beofor set")
  (get-in @the-config atts))

