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
   [f/wform
    [{:path [:form-demo :basic]}
     {:path [:form-demo :number] :type :number}
     {:path [:form-demo :boolean] :type :boolean}
     {:path [:form-demo :set] :type :set :elements [:oxygen :nitrogen :helium :polonium :tungsten]}
     {:path [:form-demo :oneof] :type :oneof :elements [:hobbit :elf :dwarf :human :ainur :ent]}
     {:path [:form-demo :select] :type :select :options ["Male" "Female" "Agender" "Polygender" "Prefer not to say"]}
     {:path [:form-demo :textarea] :type :textarea}
     {:path [:form-demo :local-files] :type :local-files}
     {:path [:form-demo :local-directory] :type :local-directory}]
    #(rf/dispatch [:flash {:message (str "Submitted: " %) :class "alert-success" }])
    ]
   [:h3 "Output"]
   [:pre
    ;; This sort of accidentally works...
    (with-out-str (pprint/pprint @(rf/subscribe [:form-field-value [:form-demo]])))]
   ]
  )


