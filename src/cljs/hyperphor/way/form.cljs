(ns hyperphor.way.form
  (:require [re-frame.core :as rf]
            [org.candelbio.multitool.core :as u]
            [ajax.url :as aurl]
            [clojure.string :as str]
            [hyperphor.way.web-utils :as wu]
            )  )


;;; Status: carved out of traverse.ops, not yet integrated
;;; TODO param stuff should go through here I guess. Or do we need both levels of abstraction?

(rf/reg-sub
 :form-field-value
 (fn [db [_ field]]
   (get-in (:form db) field)))

(rf/reg-event-db
 :set-form-field-value
 (fn [db [_ field value]]
   (assoc-in db (cons :form field) value)))

;;; ⦿| forms |⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾-⦿|⦾|⦿|⦾


;;; Create a UI for a form field.
;;; Args:
;;;   type: keyword UI type, used for dispatch (TODO allow extension, eg for special-purpose selectors)
;;;   path: keyseq identifying the value
;;;   label: optional
;;;   id: optional (HTML id)
;;;   hidden?
;;;   disabled?
;;;   a docstring (with optional HTML) (TODO)

(defmulti form-field (fn [{:keys [type path label id hidden? disabled? doc] :as args}]
                       type))

(defmulti form-field-row (fn [{:keys [type path label id hidden? disabled? doc] :as args}]
                           type))

;;; TODO labels bold, right-justified, maybe centered. See Rawsugar ops
(defmethod form-field-row :default
  [{:keys [type path label id hidden? disabled?] :as args}]
  (let [label (or label (name (last path)))
        id (or id (str/join "-" (map name path)))]
    [:div.row.mb-3
     [:label.col-sm-2.col-form-label {:for id} label]
     [:div.col-10
      (form-field (assoc args :id id :label label))
      ]]))

(defmethod form-field :default [{:keys [type path label id hidden? disabled? value-fn] :as args :or {value-fn identity}}]
  [:input.form-control
   {:id id
    :value @(rf/subscribe [:form-field-value path])
;    :disabled false
    :on-change (fn [e]
                 (rf/dispatch
                  [:set-form-field-value path (value-fn (-> e .-target .-value))]))
    ;; TODO
    #_ :on-key-press #_ (fn [evt]
                    (when (= "Enter" (.-key evt))
                      (prn :enter path)
                      nil))
    }])

;;; TODO restrict keys or show warning if content is not legal number
(defmethod form-field :number [{:keys [] :as args}]
  (form-field (assoc args :type :default :value-fn u/coerce-numeric)))

(defmethod form-field :textarea [{:keys [id path read-only]}]
  [:textarea.form-control
   {:id id
    :value @(rf/subscribe [:form-field-value path])
;    :disabled false
    :on-change (fn [e]
                 (rf/dispatch
                  [:set-form-field-value path (-> e .-target .-value)]))
    }])

;;; TODO Rich text with BlockType


;;; TODO producing react warnings
(defmethod form-field :boolean [{:keys [path id read-only doc type hidden]}]
  [:input.form-check-input
   {:id id
    :type "checkbox"
    :checked @(rf/subscribe [:form-field-value path])
    :disabled read-only
    :on-change (fn [e]
                 (rf/dispatch
                  [:set-form-field-value path (-> e .-target .-checked)]))}])


;;; TODO option processing, labels/hierarchy etc.
;;; TODO might need to translate from none-value to nil
(defmethod form-field :select [{:keys [path read-only doc hidden? options id]}]
  (let [disabled? false
        value @(rf/subscribe [:form-field-value path])
        dispatch #(rf/dispatch [:set-form-field-value path %])]
    (prn :options options)
    (wu/select-widget id value dispatch options nil disabled?)))

;;; TODO multiselect


;;; For upload
(defmethod form-field :local-files [{:keys [id path read-only doc type hidden]}]
  [:input.form-control
   {:id id
    :type "file"
    :multiple "true"                    ;TODO should be an option
    :on-change (fn [e]
                 (rf/dispatch
                  ;; TODO wrong for multiple files? Argh
                  [:set-form-field-value path (-> e .-target .-value)]))
    }])


;; --}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{--}{



;;; React multiselect widget
;;; TODO make order editable. This is possible but hairy: https://github.com/reagent-project/reagent/blob/master/examples/react-sortable-hoc/src/example/core.cljs

#_ (def react-select (reagent/adapt-react-class js/Select))

#_
(defn multi-select-widget [name options selected-values]
  [react-select
   {:value             (filter (fn [{:keys [value]}]
                                 (some #(= value %) selected-values))
                               options)
    :on-change         (fn [selected]
                         (rf/dispatch [:set-form-field-value name (map :value (js->clj selected :keywordize-keys true))]))
    :is-clearable      true
    :is-multi          true
    :options           options
    :class-name        "react-select-container"
    :class-name-prefix "react-select"
    }])
 
;;; Assumes all :hidden fields are sequences from checkboxes, which is true for now
#_
(defn hidden-ui [{:keys [read-only doc type hidden] :as arg}]
  (let [arg-name (:name arg)
        value @(rf/subscribe [:form-field-value arg-name])]
    [:span.form-control.read-only
     (inflect/pluralize (count value) (inflect/singular (name arg-name))) " checked"]
    ))

(defn form
  [{:keys [args doc op]}]
  [:div#opform                          ; Was :form, but this way Enter key doesn't cause the apocalypse
   {:enc-type "multipart/form-data"
    :method "POST"}
   (when doc
     [:div.alert.alert-piciblue doc])
   [:table.optable.table.responsive
    [:tbody
     (doall                             ;re-frame requirement
      (for [{:keys [doc hidden indent] :as arg} args]
        (let [argname (:name arg)]      ;Avoid colliding with core/name
          [:tr {:key argname}
           [:th 
            (when indent "・ ") (name argname)]
           [:td 
            (if hidden
              "hidden" #_ (hidden-ui arg)
              (form-field (assoc arg :read-only false)))]
           [:td {:style {:padding-left "5px" :min-width "200px"}} doc]]
          )))]]

   ])



;;; TODO complete → valid

(defmulti field-valid? :type)

(defmethod field-valid? :default
  [arg]
  @(rf/subscribe [:form-field-value (:name arg)]))

(defmethod field-valid? :string
  [arg]
  (not (empty? @(rf/subscribe [:form-field-value (:name arg)]))))

(defmethod field-valid? :columns
  [arg]
  (not (empty? @(rf/subscribe [:form-field-value (:name arg)]))))

(defmethod field-valid? :local-files
  [arg]
  (not (empty? @(rf/subscribe [:form-field-value (:name arg)]))))

(defmethod field-valid? :boolean
  [_]
  true)                                 ;booleans are always valid

;;; TODO file fields

(defmulti form-valid? :id)

(defmethod form-valid? :default 
  [{:keys [args] :as op-def}]
  (every?
   identity
   (for [arg args]
     (or (:optional? arg)
         (field-valid? arg)))))

#_
(defmethod form-valid? :upload-files 
  [{:keys [args] :as op-def}]
  ;; Note: the other fields should take care of themselves
  (let [arg (fn [n] (u/some-thing #(= (:name %) n) args))]
    (tu/oneof
     (field-valid? (arg :gs-path))
     (field-valid? (arg :local-files))
     (field-valid? (arg :local-directory)))))




