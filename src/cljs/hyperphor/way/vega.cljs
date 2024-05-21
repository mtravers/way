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
                        :actions true}])) ;TODO dev mode and/or config

(def vega-adapter (reagent/adapt-react-class rv/Vega))

(defn vega-view
  [spec data]
  (when data
    [vega-adapter {:data (clj->js data)
                   :spec (clj->js spec)
                   :actions true
                   }]))

;;; TODO put these in groups, but :optgroups ar broken
(def color-schemes
  [
   "blues"
   "tealblues"
   "teals"
   "greens"
   "browns"
   "greys"
   "purples"
   "warmgreys"
   "reds"
   "oranges"

   "turbo"
   "viridis"
   "inferno"
   "magma"
   "plasma"
   "cividis"
   "bluegreen"
   "bluepurple"
   "goldgreen"
   "goldorange"
   "goldred"
   "greenblue"
   "orangered"
   "purplebluegreen"
   "purpleblue"
   "purplered"
   "redpurple"
   "yellowgreenblue"
   "yellowgreen"
   "yelloworangebrown"
   "yelloworangered"
   "darkblue"
   "darkgold"
   "darkgreen"
   "darkmulti"
   "darkred"
   "lightgreyred"
   "lightgreyteal"
   "lightmulti"
   "lightorange"
   "lighttealblue"

   ;; diverging
   "blueorange"
   "brownbluegreen"
   "purplegreen"
   "pinkyellowgreen"
   "purpleorange"
   "redblue"
   "redgrey"
   "redyellowblue"
   "redyellowgreen"
   "spectral"])




