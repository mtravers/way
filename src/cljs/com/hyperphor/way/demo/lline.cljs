(ns com.hyperphor.way.demo.lline
  (:require [reagent.core :as reagent]
            [com.hyperphor.way.form :as f]
            [re-frame.core :as rf]
            ))

(defn line-component [from to params]
  (let [local-state (reagent/atom nil)]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (reset! local-state
                (js/LeaderLine. (.getElementById js/document from)
                                (.getElementById js/document to)
                                (clj->js params))))
      :component-will-unmount
      (fn []
        (.remove @local-state))
      :reagent-render
      (fn [] [:div])})))

(defn ui
  []
  [:div.row
   [:div.alert.alert-info "A demo of the use of the very cute " [:a {:href "https://anseki.github.io/leader-line/"} "LeaderLine library"] ", and how to integrate random non-react js in general."]
   [:div.col-6
    [f/form-field {:path [:llines 1] :id "foo" :style {:width "200px"} :init "Type here"}]]
   [:div.col-6
    [:div {:style {:height "100px"}}]
    (let [v @(rf/subscribe [:form-field-value [:llines 1]])]
      #_[:button#bar.button "Bar"]
      [:span#bar.border.border-primary.p-3 v])]
   [line-component "foo" "bar" {:size 5
                                :startPlug "square"
                                :endPlug "hand"
                                :dash {:animation true}
                                }]])
