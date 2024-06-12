(ns hyperphor.way.demo.forms
  (:require [hyperphor.way.form :as f]
            [clojure.pprint :as pprint]
            [re-frame.core :as rf]
            ))

;;; TODO include submit or equivalent

(defn ui
  []
  [:div
   [:h3 "Form"]
   [:form
   [f/form-field-row {:path [:form-demo :basic]}]
   [f/form-field-row {:path [:form-demo :number] :type :number}]
   [f/form-field-row {:path [:form-demo :checkbox] :type :boolean}]
   [f/form-field-row {:path [:form-demo :select] :type :select :options ["Male" "Female" "Agender" "Polygender" "Prefer not to say"]}]
   [f/form-field-row {:path [:form-demo :textarea] :type :textarea}]
   [f/form-field-row {:path [:form-demo :local-files] :type :local-files}]
    ]
   [:h3 "Output"]
   [:pre
    ;; This sort of accidentally works...
    (with-out-str (pprint/pprint @(rf/subscribe [:form-field-value [:form-demo]])))]
   ]
  )


