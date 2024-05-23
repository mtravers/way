(ns hyperphor.way.demo.heatmap2
  (:require [hyperphor.way.cheatmap :as ch]
            [hyperphor.way.params :as p]
            [hyperphor.way.feeds :as f]
            [hyperphor.way.vega :as v]
            ))

;;; TODO a lot of these aren't really great examples of clustering, find some better ones
(def datasets
  {"https://vega.github.io/editor/data/gapminder.json"
   [:year :country :fertility {:cluster-rows? false}]
   "https://vega.github.io/editor/data/barley.json"
   [:site :variety :yield { :aggregate :mean}]
   "https://vega.github.io/editor/data/movies.json" [:Distributor (keyword "Major Genre") (keyword "US Gross")]
   "https://vega.github.io/vega-lite/data/cars.json"
   [:Origin :Year :Horsepower {:cluster-cols? false :aggregate :mean}]
   "https://vega.github.io/editor/data/jobs.json"
   [:job :sex :count {:cluster-cols? false :aggregate :mean}]
   "https://raw.githubusercontent.com/kjhealy/viz-organdata/master/organdonation.csv"
   [:country :year :donors {:cluster-cols? false}]
   "https://www.cdc.gov/niosh/data/datasets/rd-1064-2023-0/files/Liver-gene-expression.csv"
   [:gene :treatment :fold_change]
   ;; 4M rows, too big to handle
   #_ "https://media.githubusercontent.com/media/ahmedmoustafa/gene-expression-datasets/main/datasets/medulloblastoma/medulloblastoma.long.tsv"
   #_[:gene :sample :expression]
   })


(def default-options
  {:aggregate :sum
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
   [:td {:colspan 4} contents]])

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
       #_ [field "URL" [:span [:input] [:button {:type :button} "Load"]]] ;TODO not yet
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
      :aggregate-fn (keyword (p/param-value :hm2 :aggregate))
      :cluster-rows? (p/param-value :hm2 :cluster-rows?)
      :cluster-cols? (p/param-value :hm2 :cluster-cols?)
      :color-scheme (p/param-value :hm2 :color-scheme)
      ;; TODO have a contol for this:cell-gap 0
      ]]))
