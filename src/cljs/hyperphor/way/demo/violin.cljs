(ns hyperphor.way.demo.violin
  (:require [hyperphor.way.violin :as vi]
            [hyperphor.way.feeds :as f])
  )

(defn ui
  []
  (let [data (f/from-url "http://localhost:9291/data/cd133.tsv")] ;TODO "data/cd133.tsv"
    [vi/violin data :final_diagnosis :feature_value {}]
    )) 
