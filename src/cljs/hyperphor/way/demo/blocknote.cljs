(ns hyperphor.way.demo.blocknote
  (:require ["@blocknote/mantine" :as bn]
            ["@blocknote/react" :as br]
            [reagent.core :as reagent]
            [re-frame.core :as rf]
            ))

(def blocknote-adapter (reagent/adapt-react-class bn/BlockNoteView))

(defn minimal
  []
  [:f>                                  ;Magic that lets the hook call work. Don't ask
   #(let [editor (br/useCreateBlockNote)]
      [:div.container                  ;TODO layout! 
       ;; https://github.com/roman01la/uix/blob/master/core/dev/uix/recipes/interop.cljc
       [:div.border
        [blocknote-adapter {:editor editor}]]]
      )])

(defn main-page
  []
  [:f>
   (fn []
     (let [editor (br/useCreateBlockNote #js {:onChange
                                     (fn [editor] (prn :editor editor))
                                     #_
                                     (fn [editor]
                                      (refx/dispatch
                                        [:store-content
                                         (js->clj
                                          (.-topLevelBlocks editor) :keywordize-keys true)])
                                        (.then (.blocksToMarkdown editor (.-topLevelBlocks editor))
                                               #(refx/dispatch [:store-markdown %])))
                                     :initialContent
                                     #js [#js {:content "can this possibly work"}]
                                     
                                     })
        ]
    [:div.container.mw-100
     [:h1 "Textiles " ]
     [:div.row
      [:div.col-6
       [:div.container                  ;TODO layout! 
        
        #_ [:button.btn {:on-click (fn [] (refx/dispatch [:set-markdown-content editor buggy-markdown]))} "Set Content"]
        #_ [:button.btn {:on-click (fn [] (refx/dispatch [:set-markdown-content editor real-markdown]))} "Set Big Content"]]

       ;; https://github.com/roman01la/uix/blob/master/core/dev/uix/recipes/interop.cljc
       [:div.border
        [blocknote-adapter {:editor editor}]]]
      [:div.col-6

       [:h4 "Structure"]
       [:form
        #_ [:input.form-check-input {:type "checkbox" :name "compact" :on-click (fn [e] (refx/dispatch [:set-compact (.-checked (.-target e)) ]))} ]
        [:label {:for "compact"} "Compact?"]
        ]
       [:pre #_ (with-out-str
               (pprint/pprint
                (refx/use-sub [:content])))]

       [:h4 "Markdown"]
       [:pre #_ (refx/use-sub [:markdown-content])]

       ]]]))])
