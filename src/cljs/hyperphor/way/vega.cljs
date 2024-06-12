(ns hyperphor.way.vega
  (:require
   [hyperphor.way.ui.config :as config]
   [reagent.core :as reagent]
   ["react-vega" :as rv]))

;;; TODO some convenient way to call vl → vega compiler

(def vega-lite-adapter (reagent/adapt-react-class rv/VegaLite))

;;; TODO data is being overused, ommiting it should not hide the thing
(defn vega-lite-view
  [spec data]
  (when data
    [vega-lite-adapter {:data (clj->js data)
                        :spec (clj->js spec)
                        :actions (config/config :dev-mode)}]))

(def vega-adapter (reagent/adapt-react-class rv/Vega))

(defn vega-view
  [spec data]
  (when data
    [vega-adapter {:data (clj->js data)
                   :spec (clj->js spec)
                   :actions (config/config :dev-mode)
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




