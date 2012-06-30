(ns org.klaverjasaichallenge.game
  (:require [org.klaverjasaichallenge.card :as cards]
            [org.klaverjasaichallenge.player :as player]
            [org.klaverjasaichallenge.ruleset :as rules])
  (:use org.klaverjasaichallenge.util))

(defrecord Hand [player cards])

(defn deal-cards
  "Deal each player an equal number of cards from the deck."
  [players deck]
  (let [number-of-cards-in-hand (int (/ (count deck) (count players)))
        hands (partition number-of-cards-in-hand deck)]
    (map #(Hand. %1 %2) players hands)))

(defn draw-trump
  "Draws a random trump, asks players if they want to play it. Eventually returns
  a map with the player that plays the trump." 
  [hands]
  (let [trumps (shuffle cards/suits)]
    (first
      (for [trump trumps, {:keys [player cards]} hands
            :let [forced-play? (>= (.indexOf trumps trump) 2)
                  voluntary-play? (player/play-trump? player cards trump)]
            :when (or forced-play? voluntary-play?)]
        {:player player :trump trump}))))

(defn play-trick
  "Returns for each player the card that they have chosen to play during the
  trick."
  [ruleset hands trump]
  (letfn [(play-card [trick-cards, {:keys [player cards]}]
            (let [card-played (player/play-card player ruleset cards trick-cards trump)]
              (if (rules/legal-card? ruleset card-played cards trick-cards trump)
                {:player player :card card-played}
                (throw (Exception. (str player " cheated with " card-played
                                        " and hand " cards))))))]
    (do (reduce (fn [trick-result hand] 
                  (let [trick-cards (map :card trick-result)]
                    (conj trick-result (play-card trick-cards hand)))) [] hands))))

(defn get-trick-winner
  "Returns the player with the highest card."
  [trick-result trump]
  (last 
    (sort-by #(cards/card-order trump (:card %)) trick-result)))

(defn update-hands
  [hands cards-to-remove]
  (map (fn [hand] (update-in hand [:cards] #(remove (set cards-to-remove) %)))
       hands))

(defn play-round
  ; TODO Work in progress...
  [ruleset players]
  (let [hands (deal-cards players (cards/deck))
        trump-player (draw-trump hands)
        trump (:trump trump-player)]
    (loop [hands hands, trick-number 1]
      (do (println "Trick" trick-number))
      (if (<= trick-number 8) 
        (let [trick-result (play-trick ruleset hands trump)
              hands (update-hands hands (map :card trick-result))
              trick-winner (get-trick-winner trick-result trump)]
          (doseq [trick-entry trick-result] (println trick-entry))
          (recur (re-order hands (.indexOf hands trick-winner)) (inc trick-number)))
      []))))

(defn play-game
  "Calls the play-round function n (number-of-rounds) times, each time letting
  another player be the round's starter."
  [ruleset players number-of-rounds]
  (for [round-number (range number-of-rounds)]
    (play-round ruleset (re-order players round-number))))
