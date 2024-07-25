(ns hyperphor.way.demo.blocknote
  (:require ["@blocknote/mantine" :as bn]
            ["@blocknote/react" :as br]
            [reagent.core :as reagent]
            [re-frame.core :as rf]
            [clojure.pprint :as pprint]
            [clojure.walk :as walk]
            [hyperphor.way.form :as wf]
            ))

;;; Demo integration of Blocknote https://www.blocknotejs.org/
;;; For an alternative, see Slate: https://github.com/ianstormtaylor/slate


(def blocknote-adapter (reagent/adapt-react-class bn/BlockNoteView))

(def some-markdown
  "# Hey
you, this is pretty basic
- isn't it?
")

(def buggy-markdown
  "- this is a toplevel block
  - this is a sub-block
- another toplevel block")


;;; Works, had to remove all foo:: val attribute lines, which aren't parsed and sometimes cause the whole thing to fail
(def real-markdown
  "- [The Real Page](https://hyperphor.com/ammdi/neovitalism)
- [Vibrant Matter](http://www.amazon.com/Vibrant-Matter-Political-Ecology-Franklin/dp/0822346338) by Jane Bennett
	- [The Philosopher Who Believes in Living Things | The New Yorker](https://www.newyorker.com/culture/annals-of-inquiry/the-philosopher-who-believes-in-living-things)
- [The Potato Chips Did It](https://omniorthogonal.blogspot.com/2011/07/potato-chips-did-it.html)
- [Élan Vital](https://omniorthogonal.blogspot.com/2011/03/elan-vital.html)
- Ref: New Materialisms: Ontology, Agency, and Politics, Diana Coole, Samantha Frost (editors)   
- Ref: Entangled Worlds: Religion, Science, and New Materialisms, Catherine Keller and Mary-Jane Rubenstein, eds 2017 
	- (via [[Erik Davis]] podcast, MJR was guest)  
	- Some distinguishing from speculative realism and OOO.  
	- > Nor do they appeal to the strictly “flat ontology” of the speculative realists, according to whom “everything exists equally—plumbers, cotton, bonobos, DVD players, and sandstone, for example.”  Like the new materialists, the speculative realists (especially those on the “object-oriented” branch) seek to unsettle philosophy’s traditional privilege of the human but reject what they call “process relationalism” insofar as it privileges “alliances” over entities, “couplings” over objects, and motion over rest. Demanding a theory of “sharp, specific units,” these thinkers proclaim the ontological equality of every discrete __thing__.
	- > For the new materialists, by contrast, ontology is “monolithic but multiply tiered”; in other words, things are not simply “equal” because things are not “things” in the first place. Rather, things—actual entities—are multiplicities, assemblages, hybrids, resonance machines, sonority clusters, intra-actions, complexities, and viscous porosities—all terms that variously express the insight that each cell, organism, vegetable, and photon is irreducibly composed of what Karen Barad would call an “intra-active” host of others. 
	- I guess this would be easy to mock but honestly I see a bit of conceptual poetry in there, I want to applaud.  
	- > Philip Clayton and Elizabeth Singleton affirm in “Agents Matter and Matter Agents” the agency of “every living being,” from “the humble eukaryotic cell” to Gaia as an open and evolving whole.
	- > The “new materialisms” currently coursing through cultural, feminist, political, and queer theories seek to displace human privilege by attending to the agency of matter itself.  
	- > Far from being passive or inert, they argue, matter acts, creates, destroys, and transforms—and, thus, is more of a process than a thing.  
	- > Calling as they do on the insights of quantum mechanics, general relativity, complexity theory, and non- linear biology to theorize matter as mattering, these thinkers work against much of what is often denigrated as “mere” materialism  
	- > Taking cues from [[Whitehead]], [[Deleuze]] and Guattari, Stengers and Prigogine, and Margulis and Sagan, the new materialists mobilize a revivified materiality against such toxic materialisms. They accomplish this in part by rejecting traditional ontological hierarchies—especially those that seek clear distinctions between spirit and matter, life and nonlife, or sentience and non-sentience.  
		- I like this. This is the humanities equivalent of a [[refactoring]]. But there's a political edge to this that \"refactoring\" doesn't capture – these aren't just categorical boundaries, but they mark a distinction between high and low. Shattering them is a political act.  
	- > It is not just that we are entangled in matter—we subjects who read, write, and ruminate on what “we” are. We are materializations entangled in other materializations; we happen in our mattering.  
	- A [[Materialism]] very different from the scientific/atheist/rationalist variety. 
")

(defn minimal
  []
  [:f> ;; Magic that lets the hook call work. See https://github.com/reagent-project/reagent/blob/master/doc/ReactFeatures.md#hooks
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
     (let [editor (br/useCreateBlockNote #js {
                                              ;; Works, but then db is out of sync
                                              #_ :initialContent
                                              #_ #js [#js {:content "can this possibly work"}]
                                              
                                              })
           ]

       [:div.container.mw-100
        [:div.alert.alert-info
         "A demo of " [:a {:href "https://www.blocknotejs.org/" } "BlockNote"] " integration"]
        [:div.row
         [:div.col-6
          [:div.container                  ;TODO layout! 
           
           [:button.btn {:on-click (fn [] (rf/dispatch [:set-markdown-content editor buggy-markdown]))} "Set Content"]
           [:button.btn {:on-click (fn [] (rf/dispatch [:set-markdown-content editor real-markdown]))} "Set Big Content"]]

          ;; https://github.com/roman01la/uix/blob/master/core/dev/uix/recipes/interop.cljc
          [:div.border
           [blocknote-adapter {:editor editor :onChange
                               (fn [editor]
                                 (let [doc (.-document editor)]
                                   (rf/dispatch
                                    [:store-content
                                     (js->clj
                                      doc :keywordize-keys true)])
                                   (.then (.blocksToMarkdownLossy editor doc)
                                          #(rf/dispatch [:store-markdown %]))))}]]]
         [:div.col-6

          [:h4 "Structure"]
          [wf/wform
           [{:path [:blocknote :format] :type :oneof :elements [:compact :full] :init :compact}]
           nil]
          [:pre (with-out-str
                  (pprint/pprint
                   @(rf/subscribe [:content])))]

          [:h4 "Markdown"]
          [:pre @(rf/subscribe [:markdown-content])]

          ]]]))])

;;; → Multitool (has a different definition)
;;; TODO val could be predicate
(defn dissoc-if
  [map key val]
  (if (= val (key map))
    (dissoc map key)
    map))

(defn compact-block
  [b]
  (let [b (-> b
              (dissoc-if :styles {})
              (dissoc-if :children [])
              (dissoc-if :content [])
              (dissoc :id)              ;I guess
              )
        b (if (:props b)
            (assoc b :props
                   (-> (:props b)
                       (dissoc-if :textColor "default")
                       (dissoc-if :backgroundColor "default")
                       (dissoc-if :textAlignment "left")))
            b)
        b (dissoc-if b :props {})]
    ;; Replace plain text blocks with a plain string
    (if (and (= 2 (count b))
             (= "text" (:type b)))
      (:text b)
      b)))

(defn compact-blocks
  [bs]
  (walk/postwalk
   (fn [thing]
     (if (map? thing)
       (compact-block thing)
       thing))
   bs))

(rf/reg-sub
 :content
 (fn [{:keys [content form]} _]
   (case (get-in form [:blocknote :format])
     :compact (compact-blocks content)
     :full content
     nil content)))

(rf/reg-sub
 :markdown-content
 (fn [{:keys [markdown]} _]
   markdown
   ))

(rf/reg-event-db
 :store-content                           ;set db from widget that is
 (fn [db [_ content]]
   (assoc db :content content)))

(rf/reg-event-db
 :store-markdown                           ;set db from widget that is
 (fn [db [_ md]]
   (assoc db :markdown md)))

(rf/reg-event-db
 :set-markdown-content
 (fn [db [_ editor markdown-string]]
   (let [newblocks (.tryParseMarkdownToBlocks editor markdown-string) ;Doc is wrong, this returns a promise
         existing (.-document editor)]
     (.then newblocks
            (fn [actual-newblocks]
              (.replaceBlocks editor existing actual-newblocks)))
     db)))








