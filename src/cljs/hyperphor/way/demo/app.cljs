(ns hyperphor.way.demo.app
  (:require
   ["react-dom/client" :refer [createRoot]]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [hyperphor.way.tabs :as tabs]
   [hyperphor.way.flash :as flash]
   [hyperphor.way.modal :as modal]
   [hyperphor.way.web-utils :as wu]
   [hyperphor.way.demo.heatmap :as hm]
   [hyperphor.way.demo.heatmap2 :as hm2]
   [org.candelbio.multitool.core :as u]
   [org.candelbio.multitool.browser :as browser]
   )) 

(def debug?
  ^boolean goog.DEBUG)

;;; TODO some of this belongs in way, not demo

;;; Generated from README.md by markdown/->hiccup and some light editing. 
(defn about
  []
  [:div.p-3
   [:p
    [:a {:href "https://github.com/mtravers/way"} "Source"]]
   [:div
    [:div.alert.alert-info "The way that can be named is not the eternal way. – Lao Tzu"]
    [:p "A base for building data-oriented websites it Clojure, ClojureScript, and Vega."]
    [:h2 {:id "features"} "Features"]
    [:ul
     [:li [:<> "Web infrastructure"]]
     [:li [:<> "Web utilities (spinners, modals, forms, etc)"]]
     [:li [:<> "Vega tooling for clustered heatmaps"]]
     [:li [:<> "Vega tooling for violin diagrams"]]
     [:li [:<> "Ag-grid wrapping for data tables"]]]
    [:h2 {:id "clustered-heatmaps"} "Clustered Heatmaps"]
    [:figure.image [:img {:src "https://raw.githubusercontent.com/mtravers/way/main/doc/assets/heatmap.png", :alt "" :style {:max-width  "500px"}}] ]
    [:p
     "Clustered heatmaps are a powerful data visualization technique that combines the functionalities of heatmaps and hierarchical clustering. A heatmap uses color to represent the values in a data matrix, allowing for an immediate visual assessment of patterns and trends. Each cell in the matrix is colored according to its value, making it easy to spot anomalies and relationships within the data."]
    [:p
     "Hierarchical clustering groups similar data points into clusters based on their characteristics, creating a tree (dendrogram)."]
    [:p
     "When these two techniques are combined, the rows and columns of the heatmap are reordered based on the clustering results, grouping similar data points together. This reordering makes the patterns and relationships within the data more apparent. Clustered heatmaps are particularly useful for identifying patterns, reducing data complexity, and revealing hidden structures within large data sets."]
    [:p "The code to generate clustered heatmaps is actually pretty simple, and lives in two files:"]
    [:ul
     [:li [:<> [:a {:href "https://github.com/mtravers/way/blob/main/src/cljs/hyperphor/way/cheatmap.cljs"} "Vega specification generator"]]]
     [:li [:<> [:a {:href "https://github.com/mtravers/way/blob/main/src/cljc/hyperphor/way/cluster.cljc"} "Clustering"]]]]
    [:p
     "A "
     [:a {:href "https://github.com/mtravers/way/blob/main/src/cljs/hyperphor/way/demo/heatmap.cljs#L167"} "simple example"]
     " of use."]
    [:h2 {:id "license"} "Credits"]
    [:p "Designed and coded by Mike Travers"]
    [:p "Copyright © 2020-24 " [:a {:href "http://hyperphor.com"} "Hyperphor"]]]])

(defn header
  []
  [:div.header
   [:h1 "Way"
    [:span.m-3 
     (when @(rf/subscribe [:loading?])
       (wu/spinner 1))]
    ]])

(rf/reg-sub
 :loading?
 (fn [db _]
   (:loading? db)))

(defn app-ui
  []
  [:div
   [modal/modal]
   [header]
   [flash/flash]
   [tabs/tabs
    :tab
    (array-map
     :home about
     :heatmap_basic hm/ui
     :heatmap_flex hm2/ui
     )]
   #_ [footer]
   ])

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   {:app "way demo"
    }))

(defn ^:dev/after-load mount-root
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (rf/clear-subscription-cache!)
  (let [root (createRoot (gdom/getElement "app"))]
    (.render root (r/as-element [app-ui]))
    )
  )

(defn ^:export init
  [& user]
  (let [params (browser/url-params)]
    (rf/dispatch-sync [::initialize-db])
    #_ (nav/start!)
    )
  (mount-root)
  )

