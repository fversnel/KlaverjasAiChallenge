(ns org.klaverjasaichallenge.score.roem-score-test
  (:require [org.klaverjasaichallenge.card :as card])
  (:use clojure.test 
        org.klaverjasaichallenge.score.roem-score))

(defn- filter-cards
  [rank-or-suit cards]
  (filter #(or (card/rank? rank-or-suit %)
               (card/suit? rank-or-suit %)) cards))

(deftest test-three-consecutive-card-score
  (let [clubs (filter-cards :clubs card/all-cards)]
    (is (= 20 (three-consecutive-cards (take 4 clubs))))))

(deftest test-four-cards-same-rank
  (let [kings (filter-cards :king card/all-cards)]
    (is (= 100 (four-high-cards-same-rank kings)))))

(deftest test-four-cards-same-low-rank
  (let [sevens (filter-cards :seven card/all-cards)]
    (is (= 0 (four-high-cards-same-rank sevens)))))

