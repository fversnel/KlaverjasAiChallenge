(ns org.klaverjasaichallenge.card)

(def suits [:clubs :hearts :diamonds :spades])
(def ranks [:seven :eight :nine :ten :jack :queen :king :ace])
(defn card [rank suit] {:rank rank :suit suit})
(defn suit? [suit card] (= (:suit card) suit))
(defn rank? [rank card] (= (:rank card) rank))
(def all-cards (for [suit suits rank ranks] (card rank suit)))

(defn deck 
  "Creates a shuffled sequence of 32 cards."
  []
  (shuffle all-cards))

(def normal-card-order {:seven 0 :eight 1 :nine 2 :jack 3 :queen 4 :king 5 :ten 6 :ace 7})
(def trump-card-order {:seven 8 :eight 9 :queen 10 :king 11 :ten 12 :ace 13 :nine 14 :jack 15})
(defn card-order [trump card]
  (if (suit? trump card) (trump-card-order (:rank card)) (normal-card-order (:rank card))))

(defn sort-cards
  "Sorts cards low to high."
  [trump cards]
  (sort-by #(card-order trump %) cards))

(defn get-highest-card
  [trump cards]
  (last (sort-cards trump cards)))
