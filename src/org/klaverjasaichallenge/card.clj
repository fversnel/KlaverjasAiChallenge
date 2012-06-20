(ns org.klaverjasaichallenge.card)

(def suits [:clubs :hearts :diamonds :spades])
(def ranks [:seven :eight :nine :ten :jack :queen :king :ace])

(defn card [rank suit] {:rank rank :suit suit})
(def all-cards (for [suit suits rank ranks] (card rank suit)))

(defn suit? [suit card] (= (:suit card) suit))
(defn rank? [rank card] (= (:rank card) rank))

(defn deck 
  "Creates a shuffled sequence of 32 cards."
  []
  (shuffle all-cards))

(defn card-order
  [card trump]
  (let [normal-card-order {:seven 0 :eight 1 :nine 2 :jack 3 :queen 4 :king 5 :ten 6 :ace 7}
        trump-card-order {:seven 8 :eight 9 :queen 10 :king 11 :ten 12 :ace 13 :nine 14 :jack 15}]
    (if (suit? trump card) (trump-card-order (:rank card)) (normal-card-order (:rank card)))))

(defn sort-cards
  "Sorts the cards low to high."
  [cards trump]
  (sort-by #(card-order % trump) cards))

(defn highest-card
  "Returns the highest card."
  [cards trump]
  (last (sort-cards cards trump)))

(defn higher-than?
  "Returns true if card1 is higher than card2, returns false otherwise."
  [card1 card2 trump]
  (> (card-order card1 trump) (card-order card2 trump)))

(defn highest-trump
  "Returns the highest trump or nil if there is no trump."
  [cards trump]
  (highest-card (filter #(suit? trump %) cards) trump))

(defn high-trump-card? 
  "Returns true if the card is higher than the highest trump in the trick-cards, false if not. 
  Also returns false if there is no trump card in the trick."
  [trick-cards trump card]
  (if (some #(suit? trump %) trick-cards)
      (higher-than? card (highest-trump trick-cards trump) trump)
      false))
