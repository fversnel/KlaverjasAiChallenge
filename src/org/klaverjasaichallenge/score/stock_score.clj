(ns org.klaverjasaichallenge.score.stock-score
  (:use org.klaverjasaichallenge.card))

(defn card-points
  "Returns the card's trump points if it's suit is equal to the trump,
  returns the card's normal points otherwise."
  [card trump]
  (let [normal-points {:seven 0 :eight 0 :nine 0 :jack 2 :queen 3 :king 4 :ten 10 :ace 11}
        trump-points {:seven 0 :eight 0 :queen 3 :king 4 :ten 10 :ace 11 :nine 14 :jack 20}]
    (if (suit? trump card) (trump-points (:rank card)) (normal-points (:rank card)))))

(defn calculate-stock-score
  "Calculates the score without roem for a given set of cards."
  [cards trump]
  (let [card-scores (map #(card-points % trump) cards)]
    (reduce + card-scores)))

