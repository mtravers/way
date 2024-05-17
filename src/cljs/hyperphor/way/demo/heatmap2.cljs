(ns hyperphor.way.demo.heatmap2
  (:require [hyperphor.way.cheatmap :as ch]
            [hyperphor.way.params :as p]
            [hyperphor.way.feeds :as f]
            ))

(def datasets
  {"https://vega.github.io/vega-lite/data/cars.json" [:Origin :Year :Horsepower]
   "https://vega.github.io/editor/data/barley.json" [:site :variety :yield]
   "https://vega.github.io/editor/data/movies.json" [:Distributor (keyword "Major Genre") (keyword "US Gross")]
   "https://vega.github.io/editor/data/gapminder.json" [:country :year :fertility]
   "https://vega.github.io/editor/data/jobs.json" [:job :sex :count]
   "https://raw.githubusercontent.com/kjhealy/viz-organdata/master/organdonation.csv" [:country :year :donors]
   "https://www.cdc.gov/niosh/data/datasets/rd-1064-2023-0/files/Liver-gene-expression.csv" [:gene :treatment :fold_change]
   })

(defn select-dataset
  [ds-url]
  (when-let [[rows cols values] (get datasets ds-url)]
    ;; TODO name is because we aren't being consistent with use of keywords
    (p/set-param-value :hm2 :rows (name rows))
    (p/set-param-value :hm2 :columns (name cols))
    (p/set-param-value :hm2 :values (name values))))

(defn field
  [label contents]
  [:tr [:th label] [:td contents]])

(defmethod f/fetch :hm2
  [_]
  )

;;; TODO changing ds or mappings can be slow, should have a spinner (not that clear how to do that)
;;; TODO allow selection of aggregation fn (+, mean, count...)
(defn ui
  []
  (let [data (f/from-url (p/param-value :hm2 :dataset))]
    [:div
     [:div.alert.alert-info "Select a dataset, then you can play around with the field mappings"]
     [:table
      #_ [field "URL" [:span [:input] [:button {:type :button} "Load"]]] ;TODO not yet
      [field "Dataset" (p/select-widget-parameter :hm2 :dataset (cons nil (keys datasets)) select-dataset)]
      [field "Row" (p/select-widget-parameter :hm2 :rows (keys (first data)))]
      [field "Column" (p/select-widget-parameter :hm2 :columns (keys (first data)))]
      [field "Values" (p/select-widget-parameter :hm2 :values (keys (first data)))]]
     [ch/heatmap data
      ;; TODO keyword should not be necessary
      ;; TODO be smarter about which fields are suitable for each (nominal vs. quantitative)
      (keyword (p/param-value :hm2 :rows))
      (keyword (p/param-value :hm2 :columns))
      (keyword (p/param-value :hm2 :values))
      ]]))
