(ns org.klaverjasaichallenge.score.stock-score-test
  (:use clojure.test 
        org.klaverjasaichallenge.score.stock-score 
        org.klaverjasaichallenge.card))

(deftest test-stock-score
  (is (= 152 (calculate-stock-score (deck) :clubs))))

