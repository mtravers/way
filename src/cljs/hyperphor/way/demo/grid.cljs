(ns hyperphor.way.demo.grid
  (:require [hyperphor.way.feeds :as f]
            [hyperphor.way.aggrid :as ag]))

(defn ui
  []
  [:div
   [:p "Minimal ag-grid example (gapminder data)"]
   (let [data (f/from-url "https://vega.github.io/editor/data/gapminder.json")]
     [ag/ag-table 
      data
      :col-defs {:country {:url-template "/country/%s"}}
      ])])
