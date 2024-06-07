(ns hyperphor.way.demo.grid
  (:require [hyperphor.way.feeds :as f]
            [hyperphor.way.aggrid :as ag]))

(defn ui
  []
  [:div
   [:p "Minimal ag-grid example"]
   (let [data (f/from-url  "https://vega.github.io/editor/data/gapminder.json")]
     [ag/ag-table 
      :demo
      (keys (first data))
      data
      ])])
