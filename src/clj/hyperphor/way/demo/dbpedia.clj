(ns hyperphor.way.demo.dbpedia
  (:require [clj-http.client :as client]
            [org.candelbio.multitool.core :as u]
            [clojure.string :as str]
            ))

;;; :@foo breaks Clojure reader, but this works
(u/defn-memoized atkey
  [key]
  (keyword (str "@" (name key))))

(defn humanize
  [term]
  (when term
    (-> term
        name
        (str/replace "_" " "))))

(defn dbpedia-link
  [ent]
  [:a {:href ent} (humanize ent)])

;;; Added to multitool
(defn join-seq
  [sep seq]
  (cond (empty? seq) '()
        (empty? (rest seq)) seq
        :else
        (cons (first seq) (cons sep (join-seq sep (rest seq))))))

;;; TODO linkify ordinary URLs
(defn render-value
  [v]
  (cond (and (map? v) (get v (atkey :value))) (get v (atkey :value))
        (and (sequential? v) (get (first v) (atkey :value)))
        (get (u/some-thing #(= "en" (get % (atkey :language)))
                           v)
             (atkey :value))
        (sequential? v) `[:span ~@(join-seq ", " (map render-value v))]
        (not (string? v)) (str v)
        (re-matches #"http://dbpedia.org/resource/(.+)" v) (dbpedia-link (second (re-matches #"http\:\/\/dbpedia.org/resource/(.+)" v)))
        :else
        (str v)))

(defn entity-content
  [ent]
  (let [ld (:body (client/get (str "http://dbpedia.org/resource/" ent)
                              {:as :json :accept "application/ld+json"}))
        graph (first (get ld (keyword "@graph")))
        ]
    [:table.table-bordered
     (for [[k v] graph]
       [:tr
        [:th (name k)]
        [:td (render-value v)]])]))

