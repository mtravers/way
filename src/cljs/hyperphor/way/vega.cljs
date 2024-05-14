(ns hyperphor.way.vega
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   ["react-vega" :as rv]))

;;; TODO some convenient way to call vl → vega compiler

(def vega-lite-adapter (reagent/adapt-react-class rv/VegaLite))

(defn vega-lite-view
  [spec data]
  (when data
    [vega-lite-adapter {:data (clj->js data)
                        :spec (clj->js spec)
                        :actions true}])) ;TODO on in dev mode

(def vega-adapter (reagent/adapt-react-class rv/Vega))

(defn vega-view
  [spec data]
  (when data
    [vega-adapter {:data (clj->js data)
                   :spec (clj->js spec)
                   ;; TODO need to generalize this
                   :signalListeners (clj->js {"click" (fn [_ v] (rf/dispatch [:vega-click v]))}) 
                   :actions true
                   }]))



