(defproject klaverjasaichallenge "0.1.0-SNAPSHOT"
  :description "Game engine for Klaverjas that allows AI's to compete with each other."
  :url "http://code.google.com/p/klaverjasaichallenge/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha5"]
                 [org.clojure/test.check "0.9.0"]]
  :main ^:skip-aot org.klaverjasaichallenge.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
