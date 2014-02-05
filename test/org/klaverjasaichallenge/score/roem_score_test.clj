(ns org.klaverjasaichallenge.score.roem-score-test
  (:require [org.klaverjasaichallenge.cards :as cards])
  (:use clojure.test
        org.klaverjasaichallenge.score.roem-score
        [org.klaverjasaichallenge.cards :only [card]]))

(defn- filter-cards
  [rank-or-suit cards]
  (filter #(or (cards/rank? rank-or-suit %)
               (cards/suit? rank-or-suit %)) cards))

(deftest test-three-consecutive-card-score
  (let [clubs (filter-cards :clubs cards/all-cards)]
    (is (consecutive-cards? 3 {:trick-cards (take 4 clubs)}))))

(deftest test-four-cards-same-rank
  (let [kings (filter-cards :king cards/all-cards)]
    (is (four-high-cards-same-rank? {:trick-cards kings}))))

(deftest test-four-cards-same-low-rank
  (let [sevens (filter-cards :seven cards/all-cards)]
    (is (not (four-high-cards-same-rank? {:trick-cards sevens})))))

(deftest test-calculate-score
  (let [cards [(card :king :hearts) (card :seven :hearts) (card :eight :clubs) (card :queen :hearts)]]
    (is (= (calculate-roem-score {:trump :hearts :trick-cards cards}) 
           (get-in roem-types [:stuk :points])))))
