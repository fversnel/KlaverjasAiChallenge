(ns org.klaverjasaichallenge.score.stock-score-test
  (:require [org.klaverjasaichallenge.cards :as cards])
  (:use clojure.test 
        org.klaverjasaichallenge.score.stock-score))

(deftest test-stock-score
  (is (= 152 (calculate-stock-score cards/all-cards :clubs))))
