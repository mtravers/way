(ns com.hyperphor.way.demo.grid
  (:require [com.hyperphor.way.feeds :as f]
            [com.hyperphor.way.aggrid :as ag]))

(defn ui
  []
  [:div
   [:p "Minimal ag-grid example (gapminder data)"]
   (let [data (f/from-url "https://vega.github.io/editor/data/gapminder.json")]
     [ag/ag-table 
      data
      :col-defs {:country {:url-template "/dbpedia/%s"}}
      ])])

(def datasets
  ["https://vega.github.io/editor/data/barley.json"
   "https://vega.github.io/editor/data/cars.json"
   "https://vega.github.io/editor/data/jobs.json"])

(defn ui-multiple
  []
  [:div
   [:p "Multiple grids with different data"]
   [:div.flex-row.d-flex
    (doall
     (for [url datasets]
       (let [data (f/from-url url)]
         [:div {:style {:height "500px" :width "500px"}}
          [ag/ag-table 
           data]])))]])
