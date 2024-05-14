(ns hyperphor.way.data
  (:require [taoensso.timbre :as log]
            [org.candelbio.multitool.core :as u]
            [org.candelbio.multitool.cljcore :as ju]
            [org.candelbio.multitool.math :as mu]
            [clojure.string :as str]
            [clojure.data.json :as json]))



(defn denil
  [thing]
  (if (nil? thing) [] thing))

;;; TODO this looks like a massive security hole. Although what harm can parsing json do?
(defn url-data
  [{:keys [url]}]
  (json/read-str (slurp url) :key-fn keyword))

(defn data
  [{:keys [data-id] :as params}]
  (log/info :data params)
  (-> (case (if (vector? data-id) (first data-id) data-id) ;TODO multimethod or some other less kludgerous form
        ;; methodize
        ;; For debugging
        "url" (url-data params)
        )
      denil))                           ;TODO temp because nil is being used to mean no value on front-end...lazy




