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

(defn about
  []
  [:div.p-3
   [:p
    "Way Demo"]
   [:p
    [:a {:href "https://github.com/mtravers/way"} "Source"]]
   ])

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

