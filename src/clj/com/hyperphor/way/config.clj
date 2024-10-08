(ns com.hyperphor.way.config
  (:require [aero.core :as aero]
            [clojure.string :as s]
            [clojure.pprint :as pprint]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            ))

;;; Generalized config mechanism. For actual vars, see resources/demo/config.edn

;;; TODO make accessible on client
;;; TODO make accessible at compile time?

(defmethod aero/reader 'split
  [_ _ [s]]
  (and s (s/split s #",")))

(def the-config (atom nil))

(defn set-config-map!
  [m]
  (reset! the-config m)
  (pprint/pprint @the-config)
  m)

(defn read-config
  [path]
  (log/info "Read config" path)
  (set-config-map! (aero/read-config (io/resource path))))

(defn config
  [& atts]
  ;; avoid common error of calling at compile time rather than run
  (assert @the-config "Config not set")
  (get-in @the-config atts))



