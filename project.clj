(defproject nbodybox "0.1.0-SNAPSHOT"
  :description "WebGL and Node Webkit conquer the world"
  :url "https://github.com/eigenhombre/n-body-box"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [enfocus "2.1.1"]]
  :source-paths ["src/clj"]
  :main ^:skip-aot nbodybox.core
  :target-path "target/%s"
  :plugins [[lein-node-webkit-build "0.1.6"]
            [lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/client.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]}
  :node-webkit-build {:root "resources/public"
                      :osx { :icon "resources/icons/myicon.icns"}
                      :name "NBodyBox"}
  :profiles {:uberjar {:aot :all}})
