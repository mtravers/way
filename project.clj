(defproject hyperphor/way "0.1.3" ;TODO have to change deploy.sh when version changes
  :description "Way"
  :url "https://shrouded-escarpment-03060-744eda4cc53f.herokuapp.com/"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :deploy-repositories [["clojars" {:sign-releases false}]]
  :plugins [[lein-shadow "0.4.1"]]
  :dependencies [[org.clojure/clojure "1.11.3"]
                 [org.candelbio/multitool "0.1.7"]
                 [org.clojure/data.json "2.5.0"]
                 [environ "1.2.0"]
                 [com.taoensso/timbre "6.5.0"]
                 [aero "1.1.6"]

                 ;; Backend
                 [clj-http "3.13.0" :exclusions [commons-io]]
                 [compojure "1.7.1"]
                 [ring "1.12.1"]
                 [ring/ring-core "1.12.1"]
                 [ring/ring-defaults "0.5.0"]
                 [ring/ring-jetty-adapter "1.12.1"]
                 [ring-basic-authentication "1.2.0"]
                 [ring-logger "1.1.1"]
                 [ring-middleware-format "0.7.5" :exclusions [javax.xml.bind/jaxb-api]]
                 [ring-oauth2 "0.1.4"]

                 ;; Data
                 [clj-http "3.13.0" :exclusions [commons-io]]
                 [org.clojure/data.csv "1.1.0"]

                 ;; frontend
                 ;; See packge.json for the real dependencies
                 #_ [org.clojure/clojurescript "1.11.132"] ;causes shadow-cljs error, who knows
                 [thheller/shadow-cljs "2.26.5"] ;TODO maybe only in dev profile
                 [reagent "1.2.0"]
                 [re-frame "1.4.3"]
                 [com.cemerick/url "0.1.1"]
                 [cljs-ajax "0.8.4"]
                 [day8.re-frame/tracing "0.6.2"]      ;TODO dev only
                 [day8.re-frame/re-frame-10x "1.9.9"] ;TODO dev only

                 ]
  :main ^:skip-aot hyperphor.way.demo.core
  :source-paths ["src/cljc" "src/clj" "src/cljs"] 
  :clean-targets ^{:protect false} ["target" ".shadow-cljs" "resources/public/cljs-out"]
  :repl-options {:init-ns hyperphor.way.demo.core}

  :profiles {:uberjar {:aot :all
                       :omit-source true
                       :prep-tasks [["shadow" "release" "app"] "javac" "compile"] ;NOTE if you omit the javac compile items, :aot stops working!
                       :resource-paths ["resources"]
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}

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
