(ns com.hyperphor.way.views.eval
  (:require [ring.util.response :as response]
            [com.hyperphor.way.config :as config]
            [com.hyperphor.way.ss-forms :as forms]
            ))

;;; Danger will robinson

(def eval-enabled? (atom false))
(def magic-hash 932508623)

(defn remote-eval
  [form magic]
  (str
   (try
     ;; Extra protection
     (assert (= (hash magic) magic-hash) "Needs more magic")
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


