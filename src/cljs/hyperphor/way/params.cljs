(ns hyperphor.way.params
  (:require [re-frame.core :as rf]
            [hyperphor.way.feeds :as f]
            [hyperphor.way.web-utils :as wu]
            ))

;;; TODO maybe generalize this pattern to -before, and other event handlers. 
(defmulti set-param-after (fn [db [_ data-id param value]] [data-id param]))

(defmethod set-param-after :default
  [db msg]
  db)

;;; Causes a new data fetch. TODO not always desired, need some flex here
(defn set-param
  [db [_ data-id param value :as msg]]
  (prn :set-param data-id param value)
  (f/fetch data-id)                   
  (set-param-after db msg)
  (-> (if (vector? param)                  ;? smell
        (assoc-in db (concat [:params data-id] param) value)
        (assoc-in db [:params data-id param] value))
      ))

(rf/reg-event-db
 :set-param
 set-param)

(rf/reg-event-db
 :set-param-if
 (fn [db [_ data-id param value :as msg]]
   (prn :set-param-if data-id param value)
   (if (if (vector? param)
         (get-in db (concat [:params data-id] param))
         (get-in db [:params data-id param]))
     db
     (set-param db msg)
     )))

(rf/reg-sub
 :param
 (fn [db [_ data-id param]]
   (if (vector? param)
     (get-in db (concat [:params data-id] param))
     (get-in db [:params data-id param]))))

(defn safe-name
  [thing]
  (when thing
    (or (.-name thing)
        thing)))

(defn select-widget-parameter
  [data-id param-id values & {:keys [default extra-action]}]
  (when default                         ;TODO propagate to other param widgets
    (rf/dispatch [:set-param-if data-id param-id (safe-name default)])) 
  (wu/select-widget
   param-id
   @(rf/subscribe [:param data-id param-id])
   #(rf/dispatch [:set-param data-id param-id %])
   ;; TODO this breaks :optgroups
   (map (fn [v]
          {:value v :label (if v (wu/humanize v)  "---")})
        values)
   nil
   nil
   {:display "inherit" :width "inherit" :margin-left "2px"}))

(defn param-value
  [data-id param-id]
  @(rf/subscribe [:param data-id param-id]))

(defn set-param-value
  [data-id param-id value]
  (rf/dispatch [:set-param data-id param-id value]))

(defn checkbox-parameter
  [data-id param-id & {:keys [label]}]
  [:div.form-check
   [:input.form-check-input
    {:name param-id
     :id param-id
     :type "checkbox"
     :checked @(rf/subscribe [:param data-id param-id])
     :on-change (fn [e]
                  (rf/dispatch
                   [:set-param data-id param-id (-> e .-target .-checked)]))}
    ]
   (when label
     [:label.form-check-label {:for param-id} label])])
