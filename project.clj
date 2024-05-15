(defproject way "0.1.0-SNAPSHOT"
  :description "Way"
  :url "http://example.com/TODO"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-shadow "0.4.1"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.candelbio/multitool "0.1.5"]
                 [org.clojure/data.json "2.5.0"]
                 [environ "1.2.0"]
                 [com.taoensso/timbre "6.3.1"]

                 ;; Backend
                 [clj-http "3.12.3" :exclusions [commons-io]]
                 [compojure "1.7.0"]
                 [ring "1.11.0"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-defaults "0.4.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [ring-logger "1.1.1"]
                 [ring-middleware-format "0.7.5" :exclusions [javax.xml.bind/jaxb-api]]
                 ;; Data
                 [clj-http "3.12.3" :exclusions [commons-io]]

                 ;; frontend
                 ;; See packge.json for the real dependencies
                 #_ [org.clojure/clojurescript "1.11.132"] ;causes shadow-cljs error, who knows
                 [thheller/shadow-cljs "2.26.5"] ;TODO maybe only in dev profile
                 [reagent "1.2.0"]
                 [re-frame "1.4.2"]
                 [com.cemerick/url "0.1.1"]
                 [cljs-ajax "0.8.0"]
                 [day8.re-frame/tracing "0.6.2"]      ;TODO dev only
                 [day8.re-frame/re-frame-10x "1.9.3"] ;TODO dev only

                 ]
  :source-paths ["src/cljc" "src/clj" "src/cljs"] 
  :clean-targets [".shadow-cljs"]
  :repl-options {:init-ns way.core}
  :shadow-cljs {:lein true
                :builds
                {:app {:target :browser
                       :compiler-options {:infer-externs true}
                       :output-dir "resources/public/cljs-out"
                       :asset-path "/cljs-out"         ;webserver path
                       :modules {:dev-main {:entries [hyperphor.way.demo.app]}}
                       :devtools {:preloads [day8.re-frame-10x.preload.react-18]}
                       :dev {:compiler-options
                             {:closure-defines
                              {re-frame.trace.trace-enabled?        true
                               day8.re-frame-10x.show-panel         false ;does not work, afaict
                               day8.re-frame.tracing.trace-enabled? true}}}}}}
  )
