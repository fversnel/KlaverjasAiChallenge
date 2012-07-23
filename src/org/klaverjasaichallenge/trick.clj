(ns org.klaverjasaichallenge.trick)

(defn get-leading-suit
  [trick-cards]
  (:suit (first trick-cards)))

(defn get-mates-card
  "Gets the player's mate's card based on the trick-cards that do not contain the player's card.
  If the player's mate did not play a card then nil is returned."
  [trick-cards]
  (cond
    (= (count trick-cards) 2) (first trick-cards)
    (= (count trick-cards) 3) (second trick-cards)
    :else nil))
