(ns hyperphor.way.ui.init
  (:require
   ["react-dom/client" :refer [createRoot]]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [hyperphor.way.ui.config :as config]
   [org.candelbio.multitool.browser :as browser]
   ))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   {:app (config/config :app-title)
    }))

(defn ^:dev/after-load mount-root
  [app-ui]
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (rf/clear-subscription-cache!)
  (let [root (createRoot (gdom/getElement "app"))]
    (.render root (r/as-element [app-ui]))
    )
  )

(defn init
  [app-ui]
  (config/init
   #(let [params (browser/url-params)]
      (rf/dispatch-sync [:initialize-db])
      #_ (nav/start!)
      (mount-root app-ui)
      )))
