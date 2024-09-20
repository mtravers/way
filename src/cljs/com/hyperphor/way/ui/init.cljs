(ns com.hyperphor.way.ui.init
  (:require
   ["react-dom/client" :refer [createRoot]]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [com.hyperphor.way.ui.config :as config]
   [org.candelbio.multitool.browser :as browser]
   ))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   {:app (config/config :app-title)
    :route {:handler :home}             ;TODO router not in Way yet, but Traverse uses this
    }))

(def root-ui (atom nil))

(defn ^:dev/after-load mount-root
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  ;; TODO gets warnings on reload but I can't figure out how to get rid of them
  (rf/clear-subscription-cache!)
  (let [root (createRoot (gdom/getElement "app"))]
    (.render root (r/as-element [@root-ui]))
    )
  )

(defn init
  [app-ui inits]
  (reset! root-ui app-ui)
  (config/init
   #(let [params (browser/url-params)]
      (rf/dispatch-sync [:initialize-db])
      #_ (nav/start!)
      (when inits (inits))
      (mount-root)
      )))
