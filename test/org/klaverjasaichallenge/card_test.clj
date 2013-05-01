(ns org.klaverjasaichallenge.card-test
  (:use clojure.test 
        org.klaverjasaichallenge.cards))

(deftest deck-size
  (is (= 32 (count all-cards))))
