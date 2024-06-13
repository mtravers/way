(ns hyperphor.way.feeds
  (:require [re-frame.core :as rf]
            [hyperphor.way.api :as api]
            [reagent.dom]
            [org.candelbio.multitool.core :as u]
            )
  )

;;; → Multitool, maybe, kind of silly. Would make more sense if you could include lists or generators in a path.
(defn assoc*
  [m k v]
  (if (= k :*)
    (u/map-values (fn [_] v) m)
    (assoc m k v)))

(defn assoc-in*
  [m [k & ks] v]
  (if ks
    (assoc* m k (assoc-in* (get m k) ks v))
    (assoc* m k v)))

;;; Data id theory
;;; [:id {param-map}]

(rf/reg-sub
 :loading?
 (fn [db _]
   (:loading? db)))

;;; Does the actual fetch, using parameters from the database
;;; TODO that is ugly and unmodular, should be fixed.
;;;    Or is it? Kind of declarative, fits in with react dataflow model
(rf/reg-event-db
 :fetch
 (fn [db [_ [data-key event-params :as data-id]]]
   (api/api-get
    "/data"
    {:params (merge (get-in db [:params data-key])
                    event-params
                    {:data-id data-key} ;TODO fix terminology to be consistent
                    )
     :handler #(rf/dispatch [::loaded data-id %])
     :error-handler #(rf/dispatch [:data-error data-id %1]) ;Override standard error handler
     })
   (-> db
       (assoc :loading? true)
       ;; blanks out view in between updates, which we don't want
       ;; (assoc-in [:data-status data-id] :fetching)
       )))

(rf/reg-event-db
 :data-error
 (fn [db [_ data-id message]]
   (rf/dispatch [:flash (if message
                        {:class "alert-danger" :message message}
                        {:show? false})])
   (-> db
       (assoc-in [:data-status data-id] :error)
       (assoc-in [:data-status-error data-id] message) ;TODO nothing looks at this yet
       (assoc :loading? false))))

(rf/reg-event-db
 :fetch-once
 (fn [db [_ data-id]]
   (when-not (get-in db [:data data-id])
     (rf/dispatch [:fetch data-id]))))

(rf/reg-sub
 :data
 (fn [db [_ data-id]]
   (let [data (or (get-in db [:data data-id]) [])]
     (case (get-in db [:data-status data-id])
       :valid data
       :fetching nil                   ; TODO unclear if it only means initial fetch or later ones
       :error []
       (:invalid nil) (do (rf/dispatch [:fetch data-id])
                          data)))))

(defmulti postload (fn [db id data] id))

(defmethod postload :default
  [db id data]
  db)

(rf/reg-event-db
 ::loaded
 (fn [db [_ data-id data]]
   (-> db
       (assoc-in [:data data-id] data)
       (assoc-in [:data-status data-id] :valid) ;not necessarily, UI could have changed while we were loading!
       (assoc :loading? false)
       (postload data-id data)
       )))

(defn from-url
  [url]
  (when url
    @(rf/subscribe [:data [:url {:url url}]])))

(defmulti fetch identity)

(defmethod fetch :default
  [data-id]
  (rf/dispatch [:fetch data-id]))
