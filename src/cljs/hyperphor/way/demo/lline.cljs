(ns hyperphor.way.demo.lline
  (:require [reagent.core :as reagent])
  )

(defn line-component [from to params]
  (reagent/create-class
   {:component-did-mount
    (fn []
      (js/LeaderLine. (.getElementById js/document from)
                      (.getElementById js/document to)
                      (clj->js params)))
    :reagent-render
    (fn [] [:div])}))

(defn ui
  []

  [:div.row
   [:div "A demo of the use of the very cute " [:a {:href "https://anseki.github.io/leader-line/"} "LeaderLine library"]]
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
