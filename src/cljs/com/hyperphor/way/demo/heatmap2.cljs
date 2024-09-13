(ns com.hyperphor.way.demo.heatmap2
  (:require [com.hyperphor.way.cheatmap :as ch]
            [com.hyperphor.way.params :as p]
            [com.hyperphor.way.feeds :as f]
            [com.hyperphor.way.vega :as v]
            [com.hyperphor.way.web-utils :as wu]
            ))

;;; TODO a lot of these aren't really great examples of clustering, find some better ones
(def datasets
  (array-map                            ;preserve order
   "https://vega.github.io/editor/data/gapminder.json"
   [:year :country :fertility {:cluster-rows? false}]

   ;; TODO – requires: symlog scale, more space for trees, labels, and the clustering still seems off
   #_"https://raw.githubusercontent.com/colinmorris/pejorative-compounds/master/counts.csv"
   #_ [:pre :suff :count {}]

   "https://vega.github.io/editor/data/barley.json"
   [:site :variety :yield { :aggregate :mean}]
   "https://vega.github.io/editor/data/movies.json" [:Distributor (keyword "Major Genre") (keyword "US Gross")]
   "https://vega.github.io/vega-lite/data/cars.json"
   [:Origin :Year :Horsepower {:cluster-cols? false :aggregate :mean}]
   "https://vega.github.io/editor/data/jobs.json"
   [:job :sex :count {:cluster-cols? false :aggregate :mean}]

   ;; Eh, and date is mis-sorted (needs to be declared temporal maybe)
   #_ "https://vega.github.io/editor/data/stocks.csv"
   #_ [:date :symbol :price {}]

   "https://raw.githubusercontent.com/kjhealy/viz-organdata/master/organdonation.csv"
   [:country :year :donors {:cluster-cols? false}]
   "https://raw.githubusercontent.com/zief0002/modeling/main/data/fertility.csv"
   [:region :gni_class :fertility_rate {}]

   "https://www.cdc.gov/niosh/data/datasets/rd-1064-2023-0/files/Liver-gene-expression.csv"
   [:gene :treatment :fold_change]
   ;; 4M rows, too big to handle
   #_ "https://media.githubusercontent.com/media/ahmedmoustafa/gene-expression-datasets/main/datasets/medulloblastoma/medulloblastoma.long.tsv"
   #_[:gene :sample :expression]

   ;; Single-colummn, special case of clustering (TODO make this a test)
   #_ "http://localhost:2219/data/barley-small.json"
   #_ [:site :variety :yield { :aggregate :mean}]
   "https://gist.githubusercontent.com/armgilles/194bcff35001e7eb53a2a8b441e8b2c6/raw/92200bc0a673d5ce2110aaad4544ed6c4010f687/pokemon.csv"
   [:type_1 :generation :speed]
   ))


(def default-options
  {:aggregate :mean
   :cluster-rows? true
   :cluster-cols? true})

(defn select-dataset
  [ds-url]
  (when-let [[rows cols values options] (get datasets ds-url)]
      ;; TODO name is because we aren't being consistent with use of keywords
    (when rows (p/set-param-value :hm2 :rows (name rows)))
    (when cols (p/set-param-value :hm2 :columns (name cols)))
    (when values (p/set-param-value :hm2 :values (name values)))
    (let [{:keys [aggregate cluster-rows? cluster-cols?]} (merge default-options options)]
      (p/set-param-value :hm2 :aggregate (name aggregate))
      (p/set-param-value :hm2 :cluster-rows? cluster-rows?)
      (p/set-param-value :hm2 :cluster-cols? cluster-cols?)
      )))

(defn field
  [label contents extra]
  [:tr
   [:th label]
   [:td {:style {:width "200px"}} contents]
   [:td extra]])

(defn field2
  [label contents]
  [:tr
   [:th {:scope "row"} label]
   [:td {:colSpan 4} contents]])

(defmethod f/fetch :hm2
  [_]
  )

(defn aggregation-selector
  []
  [:span.parameter                      ;TODO parameter fns should include this I think (except sometimes you want table layout, argh)
   [:span.plabel "Aggregate by"]
   (p/select-widget-parameter :hm2 :aggregate [:sum :mean :count])])

(defn color-scheme-selector
  []
  [:span.parameter
   [:span.plabel "Color scheme"]
   (p/select-widget-parameter :hm2 :color-scheme v/color-schemes :default "redyellowblue")
   [:a.px-2 {:href "https://vega.github.io/vega/docs/schemes/#greys" :target "_blank"} "ref"]])

;;; TODO propagate this pattern to other event handlers?
;;; Also note, it isn't really "after"
(defmulti set-param-after (fn [db [_ data-id param value]] [data-id param]))

(defmethod p/set-param-after [:hm2 :dataset]
  [db [_ data-id param value]]
  (select-dataset value)
  db)

;;; TODO changing ds or mappings can be slow, should have a spinner (not that clear how to do that)
(defn ui
  []
  (let [data (f/from-url (p/param-value :hm2 :dataset))]
    [:div
     [:table.table.table {:style {:width "400px"}}
      [:tbody
       ;; Doesn't work at all
       #_ [field2 "Dataset" (p/combo-widget-parameter :hm2 :dataset (keys datasets))]
       ;; Better but still issues (like, not having good field settings for a random data url breaks)
       #_ [field2 "URL" (p/text-parameter :hm2 :dataset)]
       [field2 "Dataset" (p/select-widget-parameter :hm2 :dataset (keys datasets) :default (first (keys datasets)))]
       [field "Row" (p/select-widget-parameter :hm2 :rows (keys (first data))) (p/checkbox-parameter :hm2 :cluster-rows? :label "cluster?")]
       [field "Column" (p/select-widget-parameter :hm2 :columns (keys (first data))) (p/checkbox-parameter :hm2 :cluster-cols? :label "cluster?")]
       [field "Values"
        (p/select-widget-parameter :hm2 :values (keys (first data)))
        [:span
         [aggregation-selector]
         [color-scheme-selector]]]]]
     [ch/heatmap data
      ;; TODO keyword should not be necessary
      ;; TODO be smarter about which fields are suitable for each (nominal vs. quantitative)
      (keyword (p/param-value :hm2 :rows))
      (keyword (p/param-value :hm2 :columns))
      (keyword (p/param-value :hm2 :values))
      {:aggregate-fn (keyword (p/param-value :hm2 :aggregate))
       :cluster-rows? (p/param-value :hm2 :cluster-rows?)
       :cluster-cols? (p/param-value :hm2 :cluster-cols?)
       :color-scheme (p/param-value :hm2 :color-scheme)
       ;; TODO have a contol for this:cell-gap 0
       #_ :patches #_ [[{:orient :bottom :scale :sx}
                        {:labelAngle 45}]]}
      ]]))
