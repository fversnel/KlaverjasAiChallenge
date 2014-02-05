(ns org.klaverjasaichallenge.score.stock-score
  (:require [org.klaverjasaichallenge.cards :as cards]))

(def points 
  {:normal {:seven 0 :eight 0 :nine 0 :jack 2 :queen 3 :king 4 :ten 10 :ace 11}
   :trump {:seven 0 :eight 0 :queen 3 :king 4 :ten 10 :ace 11 :nine 14 :jack 20}
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
