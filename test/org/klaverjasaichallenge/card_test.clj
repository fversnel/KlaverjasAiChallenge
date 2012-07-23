(ns org.klaverjasaichallenge.card-test
  (:use clojure.test 
        org.klaverjasaichallenge.card))

(deftest deck-size
  (is (= 32 (count (deck)))))
