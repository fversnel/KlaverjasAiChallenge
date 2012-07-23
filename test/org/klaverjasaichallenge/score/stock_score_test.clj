(ns org.klaverjasaichallenge.score.stock-score-test
  (:require [org.klaverjasaichallenge.card :as card])
  (:use clojure.test 
        org.klaverjasaichallenge.score.stock-score))

(deftest test-stock-score
  (is (= 152 (calculate-stock-score (card/deck) :clubs))))
