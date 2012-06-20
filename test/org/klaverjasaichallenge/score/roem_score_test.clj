(ns org.klaverjasaichallenge.score.roem-score-test
  (:use clojure.test 
        org.klaverjasaichallenge.score.roem-score 
        org.klaverjasaichallenge.card))

(defn- filter-cards
  [rank-or-suit cards]
  (filter #(or (rank? rank-or-suit %)
               (suit? rank-or-suit %)) cards))

(deftest test-three-consecutive-card-score
  (let [clubs (filter-cards :clubs all-cards)]
    (is (= 20 (three-consecutive-cards (take 4 clubs))))))

(deftest test-four-cards-same-rank
  (let [kings (filter-cards :kings all-cards)]
    (is (= 100 (four-high-cards-same-rank kings)))))

(deftest test-four-cards-same-low-rank
  (let [sevens (filter-cards :seven all-cards)]
    (is (= 0 (four-high-cards-same-rank sevens)))))

