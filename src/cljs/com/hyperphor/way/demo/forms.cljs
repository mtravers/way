(ns com.hyperphor.way.demo.forms
  (:require [com.hyperphor.way.form :as f]
            [clojure.pprint :as pprint]
            [re-frame.core :as rf]
            ))

;;; TODO include submit or equivalent
(defn ui
  []
  [:div
   [:h3 "Form"]
   [f/wform
    [{:path [:form-demo :basic] :doc "A basic text field"}
     {:path [:form-demo :number] :type :number :doc "A numeric field"}
     {:path [:form-demo :boolean] :type :boolean :doc "Yes or no"}
     {:path [:form-demo :set]
      :type :set
      :elements [:oxygen :nitrogen :helium :polonium :tungsten]
      :doc "Any or all of a fixed set "}
     {:path [:form-demo :oneof]
      :type :oneof
      :elements [:hobbit :elf :dwarf :human :ainur :ent]
      :doc [:span "One or a fixed set " [:a {:href "https://en.wikipedia.org/wiki/Middle-earth_peoples" :target "_ref"} "ref"]]}
     {:path [:form-demo :select] :type :select :options ["Male" "Female" "Agender" "Polygender" "Prefer not to say"] :doc [:span [:b "Also"] " one or a fixed set"]}
     {:path [:form-demo :textarea] :type :textarea :doc "Bigger text"}
     {:path [:form-demo :local-files] :type :local-files :doc "Local files for uploading"}
     {:path [:form-demo :local-directory] :type :local-directory :doc "A local directory for uploading"}]
    #(rf/dispatch [:flash {:message (str "Submitted: " %) :class "alert-success" }])
    ]
   [:h3 "Output"]
   [:pre
    ;; This sort of accidentally works...
    (with-out-str (pprint/pprint @(rf/subscribe [:form-field-value [:form-demo]])))]
   ]
  )


