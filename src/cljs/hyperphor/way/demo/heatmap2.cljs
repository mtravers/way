(ns hyperphor.way.demo.heatmap2
  (:require [hyperphor.way.cheatmap :as ch]
            [hyperphor.way.params :as p]
            [hyperphor.way.feeds :as f]
            ))

(def datasets
  {
   "https://vega.github.io/editor/data/barley.json"
   [:site :variety :yield { :aggregate :mean}]
   "https://vega.github.io/editor/data/movies.json" [:Distributor (keyword "Major Genre") (keyword "US Gross")]
   "https://vega.github.io/editor/data/gapminder.json"
   [:year :country :fertility {:cluster-rows? false}]
   "https://vega.github.io/vega-lite/data/cars.json"
   [:Origin :Year :Horsepower {:cluster-cols? false :aggregate :mean}]
   "https://vega.github.io/editor/data/jobs.json"
   [:job :sex :count {:cluster-cols? false :aggregate :mean}]
   "https://raw.githubusercontent.com/kjhealy/viz-organdata/master/organdonation.csv"
   [:country :year :donors {:cluster-cols? false}]
   "https://www.cdc.gov/niosh/data/datasets/rd-1064-2023-0/files/Liver-gene-expression.csv" [:gene :treatment :fold_change]
   })

(def default-options
  {:aggregate :sum
   :cluster-rows? true
   :cluster-cols? true})

(defn select-dataset
  [ds-url]
  (when-let [[rows cols values options] (get datasets ds-url)]
      ;; TODO name is because we aren't being consistent with use of keywords
    (p/set-param-value :hm2 :rows (name rows))
    (p/set-param-value :hm2 :columns (name cols))
    (p/set-param-value :hm2 :values (name values))
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
  [:span "Aggregate by" (p/select-widget-parameter :hm2 :aggregate [:sum :mean :count])])

;;; TODO changing ds or mappings can be slow, should have a spinner (not that clear how to do that)
(defn ui
  []
  (let [data (f/from-url (p/param-value :hm2 :dataset))]
    [:div
     [:div.alert.alert-info "Select a dataset, then you can play around with the field mappings"]
     [:table.table.table-sm {:style {:width "400px"}}
      [:tbody
       #_ [field "URL" [:span [:input] [:button {:type :button} "Load"]]] ;TODO not yet
       [field2 "Dataset" (p/select-widget-parameter :hm2 :dataset (cons nil (keys datasets)) select-dataset)]
       [field "Row" (p/select-widget-parameter :hm2 :rows (keys (first data))) (p/checkbox-parameter :hm2 :cluster-rows? :label "cluster?")]
       [field "Column" (p/select-widget-parameter :hm2 :columns (keys (first data))) (p/checkbox-parameter :hm2 :cluster-cols? :label "cluster?")]
       [field "Values"
        (p/select-widget-parameter :hm2 :values (keys (first data)))
        [aggregation-selector]]]]
     [ch/heatmap data
      ;; TODO keyword should not be necessary
      ;; TODO be smarter about which fields are suitable for each (nominal vs. quantitative)
      (keyword (p/param-value :hm2 :rows))
      (keyword (p/param-value :hm2 :columns))
      (keyword (p/param-value :hm2 :values))
      :aggregate-fn (keyword (p/param-value :hm2 :aggregate))
      :cluster-rows? (p/param-value :hm2 :cluster-rows?)
      :cluster-cols? (p/param-value :hm2 :cluster-cols?)
      ]]))
