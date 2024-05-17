(ns hyperphor.way.params
  (:require [re-frame.core :as rf]
            [hyperphor.way.feeds :as f]
            [hyperphor.way.web-utils :as wu]
            ))

;;; Causes a new data fetch.
(rf/reg-event-db
 :set-param
 (fn [db [_ data-id param value]]		
   (prn :set-param data-id param value)
   (f/fetch data-id)
   (-> (if (vector? param)                  ;? smell
         (assoc-in db (concat [:params data-id] param) value)
         (assoc-in db [:params data-id param] value))
       )))

(rf/reg-event-db
 :set-param-if
 (fn [db [_ data-id param value]]		
   (prn :set-param-if data-id param value)
   (if (if (vector? param)
         (get-in db (concat [:params data-id] param))
         (get-in db [:params data-id param]))
     db
     (if (vector? param)                  ;? smell
       (assoc-in db (concat [:params data-id] param) value)
       (assoc-in db [:params data-id param] value)))))

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
  [data-id param-id values & [extra-action]]
  ;; TODO Something wrong, smelly about this. And doesn't always work
  #_  ;; wrong for here and maybe for everything
  (when (not (empty? values))
    ;; -if removal seems to fix things? This is wrong and breaks updates
    (rf/dispatch [:set-param-if data-id param-id (safe-name (first values))])) ;TODO smell? But need to initialize somewhere
  (wu/select-widget
   param-id
   @(rf/subscribe [:param data-id param-id])
   #(do
      (rf/dispatch [:set-param data-id param-id %])
      (when extra-action (extra-action %) )) ;ugn
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
