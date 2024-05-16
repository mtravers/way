(ns hyperphor.way.config
  (:require [aero.core :as aero]
            [clojure.string :as s]
            [clojure.pprint :as pprint]
            [clojure.tools.logging :as log]
            ))

(defmethod aero/reader 'split
  [_ _ [s]]
  (and s (s/split s #",")))

(def the-config (atom {}))

(defn set-config-map!
  [m]
  (reset! the-config m)
  (pprint/pprint @the-config)
  m)

(defn read-config
  [path]
  (log/info "Read config" path)
  (set-config-map! (aero/read-config path)))

(defn config
  [& atts]
  (if atts
    (get-in @the-config atts)
    @the-config))



