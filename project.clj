(defproject binaryage/zones-sample "0.1.0-SNAPSHOT"
  :description "An example integration of cljs-zones"
  :url "https://github.com/binaryage/cljs-zones-sample"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89"]
                 [org.clojure/core.async "0.2.385"]
                 [binaryage/zones "0.1.0"]
                 [binaryage/devtools "0.8.1"]
                 [cljs-http "0.1.41"]
                 [environ "1.1.0"]
                 [figwheel "0.5.4-7"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.4-7"]
            [lein-shell "0.5.0"]
            [lein-environ "1.1.0"]]

  ; =========================================================================================================================

  :source-paths ["src/demo"]

  :clean-targets ^{:protect false} ["resources/public/_compiled"
                                    "target"]

  :checkout-deps-shares ^:replace []                                                                                          ; http://jakemccrary.com/blog/2015/03/24/advanced-leiningen-checkouts-configuring-what-ends-up-on-your-classpath/

  ; =========================================================================================================================

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {; --------------------------------------------------------------------------------------------------------------
             :demo
             {:cljsbuild {:builds {:demo
                                   {:source-paths ["src/demo"]
                                    :compiler     {:output-to     "resources/public/_compiled/demo/main.js"
                                                   :output-dir    "resources/public/_compiled/demo"
                                                   :asset-path    "_compiled/demo"
                                                   :main          zones-sample.demo
                                                   :preloads      [devtools.preload]
                                                   :optimizations :none
                                                   :source-map    true}}}}}
             ; --------------------------------------------------------------------------------------------------------------
             :demo-advanced
             {:cljsbuild {:builds {:demo-advanced
                                   {:source-paths ["src/demo"]
                                    :compiler     {:output-to     "resources/public/_compiled/demo_advanced/main.js"
                                                   :output-dir    "resources/public/_compiled/demo_advanced"
                                                   :asset-path    "_compiled/demo_advanced"
                                                   :main          zones-sample.demo
                                                   :pseudo-names  true
                                                   :optimizations :advanced}}}}}
             ; --------------------------------------------------------------------------------------------------------------
             :checkouts
             {:checkout-deps-shares ^:replace [:source-paths
                                               :test-paths
                                               :resource-paths
                                               :compile-path
                                               #=(eval leiningen.core.classpath/checkout-deps-paths)]
              :cljsbuild            {:builds {:demo
                                              {:source-paths ["checkouts/cljs-zones/src/lib"]}}}}
             ; --------------------------------------------------------------------------------------------------------------
             :figwheel
             {:figwheel  {:server-port     7000
                          :server-logfile  ".figwheel/server.log"}
              :cljsbuild {:builds {:demo {:figwheel true}}}}}

  ; =========================================================================================================================

  :aliases {"demo"                           ["with-profile" "+demo,+demo-advanced"
                                              "do"
                                              "clean,"
                                              "cljsbuild" "once,"
                                              "shell" "scripts/dev-server.sh,"]
            "devel"                          ["with-profile" "+demo,+demo-advanced,+checkouts,+figwheel"
                                              "figwheel"]})
