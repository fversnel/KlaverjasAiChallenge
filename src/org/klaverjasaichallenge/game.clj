(ns org.klaverjasaichallenge.game
  (:require [org.klaverjasaichallenge.card :as cards]
            [org.klaverjasaichallenge.player :as player]
            [org.klaverjasaichallenge.ruleset :as rules])
  (:use [org.klaverjasaichallenge.util :only [re-order]]))

(defrecord Hand [player cards])

(defn deal-cards
  "Deal each player an equal number of cards from the deck."
  [players deck]
  (let [number-of-cards-in-hand (int (/ (count deck) (count players)))
        cards (partition number-of-cards-in-hand deck)]
    (map #(Hand. %1 %2) players cards)))

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

(defn play-card
  "Asks the player to play a card on the trick."
  [ruleset trump trick {:keys [player cards]}]
  (let [trick-cards (map :card trick)
        card-played (player/play-card player ruleset cards trick-cards trump)]
    (if (rules/legal-card? ruleset card-played cards trick-cards trump)
      (conj trick {:player player :card card-played})
      (throw (Exception. (str player " cheated with " card-played
                              " and hand " (seq cards)))))))

(defn play-trick
  "Returns for each player the card that they have chosen to play during the
  trick."
  [ruleset hands trump]
  (do (reduce (fn [trick hand] (play-card ruleset trump trick hand)) 
              [] hands)))

(defn update-hands
  "Removes the cards from the hands."
  [cards-to-remove hands]
  (map (fn [hand] (update-in hand [:cards] #(remove (set cards-to-remove) %)))
       hands))

(defn play-round
  "Plays a full round (eight tricks) of klaverjas."
  [hands, {:keys [trump] :as trump-player}, play-trick]
  (loop [hands hands, tricks []]
    (if (< (count tricks) 8) 
      (let [trick (play-trick hands trump)
            trick-cards (map :card trick)
            hands (update-hands trick-cards hands)
            highest-card (cards/get-highest-card trump trick-cards)]
        (recur (re-order hands (.indexOf trick-cards highest-card)) 
               (conj tricks trick)))
      tricks)))

(defn score-round
  [tricks]
  )

(defn play-game
  "Calls the play-round function n (number-of-rounds) times, each time letting
  another player be the round's starter."
  [ruleset players number-of-rounds]
  (for [round-number (range number-of-rounds)]
    (let [players (re-order players round-number)
          hands (deal-cards players (cards/deck))
          trump-player (draw-trump hands)]
      (play-round hands trump-player #(play-trick ruleset %1 %2)))))
