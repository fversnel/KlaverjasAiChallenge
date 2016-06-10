(ns org.klaverjasaichallenge.score.stock-score
  (:require [org.klaverjasaichallenge.cards :as cards]))

(def points
  {:normal {:7 0 :8 0 :9 0 :J 2 :Q 3 :K 4 :10 10 :A 11}
   :trump {:7 0 :8 0 :Q 3 :K 4 :10 10 :A 11 :9 14 :J 20}
   :last-trick 10
   :march 100})

(defn get-card-points
  "Returns the card's trump points if it's suit is equal to the trump,
  returns the card's normal points otherwise."
  [trump card]
  (get-in points [(cards/card-type trump card) (:rank card)]))

(defn calculate-stock-score
  "Calculates the score without roem for a given set of cards."
  [trump cards]
  (->> cards
    (map (partial get-card-points trump))
    (reduce +)))
