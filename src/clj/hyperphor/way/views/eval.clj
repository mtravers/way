(ns hyperphor.views.eval
  (:require [ring.util.response :as response]
            [hyperphor.way.config :as config]
            [mgen.ui.forms :as forms]
            ))

;;; Danger will robinson

(def eval-enabled? (atom false))

(defn remote-eval
  [form magic]
  (str
   (try
     ;; Extra protection
     (assert (= (hash magic) 488645444) "Needs more magic")
     (eval (read-string form)) ;danger will robinson
     (catch Exception e
       {:status 500
        :headers {}
        :body (str e)}))))

(defn eval-page
  [req]
  (if (and
       (config/config :dev-mode)
       @eval-enabled?)
    (let [params (:params req)
          form (:form params)
          magic (:magic params)
          result (and form
                      (remote-eval form magic))
          ]
      [:div
       (forms/aform
        params
        [{:path [:form] :type :textarea}
         {:path [:magic]}
         ]
        :edit? true
        :submit "Eval"
        :cancel? false)

       (when result
       [:div
        [:h4 "Result"]
        [:pre result]])])
    [:h3 "Nothing to see here"]))


