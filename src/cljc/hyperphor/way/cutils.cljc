(ns hyperphor.way.cutils
  (:require [clojure.string :as str]
            [cemerick.url :as url]
            [clojure.string :as str]
            [org.candelbio.multitool.core :as u]
            )
  )

;;; Cljc web utils

;;; Generalize?
;;; CLJC
(defn humanize
  [term]
  (when term
    (-> term
        name
        (str/replace "_" " "))))

;;; See https://material.io/resources/icons/
(defn icon
  [icon tooltip handler & {:keys [class disabled?] :or {class "md-dark"}}]
  [:i.icon.material-icons.vcenter
   {:class (str/join " " (list class (if disabled? "md-inactive" "")))
    :on-click handler
    :data-toggle "tooltip"
    :title tooltip}
   icon])
