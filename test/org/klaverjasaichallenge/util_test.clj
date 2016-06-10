(ns org.klaverjasaichallenge.util-test
  (:use clojure.test 
        org.klaverjasaichallenge.util))

(def players [:aap :pipo :beer :mamaloe])

(deftest test-re-order-players
  (is (= (re-order players (.indexOf players :beer))
         [:beer :mamaloe :aap :pipo])))
