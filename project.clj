(defproject kleiner-driver "0.1.0"
  :description "Backend to generate fake cases and send them to router, along with API to control it."
  :url "http://example.com/FIXME"
  :main driver.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [mount "0.1.10"]

                 ; logging
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.7"]

                 [http-kit "2.2.0"]
                 [cheshire "5.6.3"]
                 [compojure "1.5.1"]
                 [org.clojure/core.async "0.2.395"]
                 [environ "1.1.0"]
                 [amalloy/ring-buffer "1.2.1"]]

  :plugins [[lein-environ "1.0.1"]
            [lein-var-file "0.3.1"]]

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :jvm-opts ["-Xmx1g" "-server" "-Duser.timezone=UTC"]
             ;"-Djavax.net.debug=ssl" ;good to enable when debugging ssl issues
  :repl-options {:timeout 60000}

  :profiles {:test {:env {:foo "BAR"}
                    :dependencies [[org.clojure/tools.namespace "0.2.11"]]}
             :uberjar {:aot [driver.core]}})
