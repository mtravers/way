(ns hyperphor.way.demo.lline
  (:require [reagent.core :as reagent]
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
   [:div "A demo of the use of the very cute " [:a {:href "https://anseki.github.io/leader-line/"} "LeaderLine library"] ", and how to integrate random non-react js in general."]
   [:div.col-6
    [:button#foo.button "Foo"]]
   [:div.col-6
    [:div {:style {:height "100px"}}]
    [:button#bar.button "Bar"]]
   [line-component "foo" "bar" {:size 5
                                :startPlug "square"
                                :endPlug "hand"
                                :dash {:animation true}
                                }]])
