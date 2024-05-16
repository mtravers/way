(ns hyperphor.way.demo.core
  (:gen-class)
  (:require [hyperphor.way.server :as server]
            [hyperphor.way.handler :as handler]
            [org.candelbio.multitool.cljcore :as ju]
            [taoensso.timbre :as log]
            [hyperphor.way.config :as config]
            [environ.core :as env]))

(defn -main
  [& args]
  (let [port (or (first args) (env/env :port) )]
    (log/info "Starting server on port" port)
    (config/read-config "resources/demo/config.edn")
    (server/start (Integer. port) handler/app)
    ;; Smart enough to be a no-op on server
    (ju/open-url (format "http://localhost:%s" port))
    ))

