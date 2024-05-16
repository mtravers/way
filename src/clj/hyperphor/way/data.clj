(ns hyperphor.way.data
  (:require [taoensso.timbre :as log]
            [org.candelbio.multitool.core :as u]
            [org.candelbio.multitool.cljcore :as ju]
            [org.candelbio.multitool.math :as mu]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv])
    (:import [org.apache.commons.io.input BOMInputStream]))


;;; From Voracious
(defn read-csv-file [fname & {:keys [separator quote headers] :as opts}]
  (with-open [reader (-> fname
                         io/input-stream
                         BOMInputStream. ;Removes garbage character
                         io/reader)]
    (doall
     ;; TODO default separator based on filename
     (csv/read-csv reader opts))))

(defn denil
  [thing]
  (if (nil? thing) [] thing))

(defn nana
  [v]
  (if (= v "NA") nil v))

;;; → Multitool
(defn file-ext
  [path]
  (second (re-matches #".*\.(\w+)$" path)))


(defn coerce-numeric
  [ds]
  (map #(u/map-values u/coerce-numeric %) ds))

;;; TODO this looks like a massive security hole. Although what harm can parsing json do?
(defn url-data
  [{:keys [url]}]
  (let [ext (file-ext url)]
    (case ext
      "json" (-> url
                 slurp
                 (json/read-str :key-fn keyword)
                 coerce-numeric)
      "csv" (->> url
                 ju/read-csv-maps       ;TODO wildly inefficient, rationalize all this
                 (map u/dehumanize)
                 coerce-numeric
                 (map #(u/map-values nana %)) ;comparing NA and numbers breaks things
                 ))))

(defn data
  [{:keys [data-id] :as params}]
  (log/info :data params)
  (-> (case (if (vector? data-id) (first data-id) data-id) ;TODO multimethod or some other less kludgerous form
        ;; methodize
        ;; For debugging
        "url" (url-data params)
        )
      denil))                           ;TODO temp because nil is being used to mean no value on front-end...lazy




