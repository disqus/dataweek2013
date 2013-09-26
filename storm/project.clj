(defproject dataweek "0.1.0-SNAPSHOT"
  :description "Disqus Dataweek 2013 Demo"
  :url "http://disqus.com/"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.scala-lang/scala-library "2.9.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 ]
  :profiles {:dev
             {:dependencies [[storm "0.9.0-wip16"]
                             [org.clojure/clojure "1.4.0"]
                             ]}
             }
  :plugins [[haruyama/lein-scalac "0.1.1"]
            [haruyama/lein-scalatest "0.0.4"]
            ]
  :scala-source-paths ["src/scala"]
  :scala-test-paths ["src/scala/test"]
  :scala-version "2.10.2"
  :min-lein-verion "2.0.0"
            )
