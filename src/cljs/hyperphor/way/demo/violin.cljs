(ns hyperphor.way.demo.violin
  (:require [hyperphor.way.violin :as vi]
            [hyperphor.way.feeds :as f])
  )

(defn ui
  []
   (let [data (f/from-url "https://shrouded-escarpment-03060-744eda4cc53f.herokuapp.com/data/cd133.tsv")]
    [vi/violin data :final_diagnosis :feature_value {}]
    )) 
