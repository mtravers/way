(ns com.hyperphor.way.navigate
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [re-frame.core :as rf]
            [org.candelbio.multitool.core :as u]
            [clojure.string :as str]
            [cemerick.url :as url]
            ))

    (array-map
     :home about
     :data_grid grid/ui
     :multiple_grid grid/ui-multiple
     :forms forms/ui
     :data_flow lline/ui
     :heatmap_basic hm/ui
     :heatmap_flex hm2/ui
     :violin vi/ui
     :rich_text bn/main-page
     )

(def routes                             ;TEMP, just do one tab thing
  ["way/" [keyword :tab]])

(defn- parse-url
  [url]
  ;; Do URL decode on components
  (u/map-values #(if (string? %)
                   (url/url-decode %)
                   %)
                (bidi/match-route c/routes url)))

(defn url-for
  [page & args]
  (apply (partial bidi/path-for routes) page (map #(if (map? %) (:db/id %) %) args)))

(def non-naviable-urls #{"/download" "/file"})

;;; Copy of native version from pushy, but will skip non-naviable-urls.
(defn- processable-url?
  [uri]
  (and (not (str/blank? uri)) ;; Blank URLs are not processable.
       (or (and (not (.hasScheme uri)) (not (.hasDomain uri))) ;; By default only process relative URLs + URLs matching window's origin
           (some? (re-matches (re-pattern (str "^" (.-origin js/location) ".*$"))
                              (str uri))))
       (not (get non-naviable-urls (.getPath uri)))
       ))

(defn start!
  []
  ;; pushy is here to take care of nice looking urls. Normally we would have to
  ;; deal with #. By using pushy we can have '/about' instead of '/#/about'.
  ;; pushy takes three arguments:
  ;; dispatch-fn - which dispatches when a match is found
  ;; match-fn - which checks if a route exist
  ;; identity-fn (optional) - extract the route from value returned by match-fn
  (pushy/start! (pushy/pushy dispatch-route
                             parse-url
                             :processable-url? processable-url?)))

(def history (pushy/pushy dispatch-route (partial bidi/match-route routes)))

;;; way harder than it should be. I hate bidi.

(defn navigate-to-url [url]
  (let [real-route (parse-url url)]
    (dispatch-route real-route)
    (pushy/set-token! history url)))

(defn navigate-to-route [route]
  (navigate-to-url (apply url-for route)))

(rf/reg-event-fx
 :navigate-url
 (fn [_ [_ url]]
   (navigate-to-url url)
   {}))

(rf/reg-event-fx
 :navigate-route
 (fn [_ [_ & route]]
   (navigate-to-route route)
   {}))
