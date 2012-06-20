(ns org.klaverjasaichallenge.game
  (:require [org.klaverjasaichallenge.card :as cards]
            [org.klaverjasaichallenge.player :as player]
            [org.klaverjasaichallenge.ruleset :as rules]))

(defn reorganize-players
  ; TODO With a bit of renaming this could also be a generic function, think about it.
  "Sorts the players by putting the new-first-player in the front while
  keeping the order intact. For example: (reorganize-players [:a :b :c :d] :c)
  returns [:c :d :a :b]."
  [players new-first-player]
  {:pre [(some #{new-first-player} players)]}
  (->> players cycle 
               (drop (.indexOf players new-first-player))
               (take (count players))))

(defn deal-cards
  "Deal each player an equal number of cards from the deck. If the number of cards to deal is
  non-divisible over the players leftover cards will be discarded. Returns a map with the
  players as keys and the cards dealt as values."
  [players deck]
  (let [number-of-cards-in-hand (int (/ (count deck) (count players)))
        hands (partition number-of-cards-in-hand deck)]
    (zipmap players hands)))

(defn draw-trump
  "Draws a trump, asks players if they want to play it, and eventually returns a map with the player
  (key) that plays the trump (value)." 
  [hands]
  (let [players (keys hands), available-trumps (shuffle cards/suits)]
    (first
      (for [chosen-trump available-trumps, player players
            :let [hand (get-in hands player)
                  forced-play? (>= (.indexOf available-trumps chosen-trump) 2)
                  voluntary-play? (player/play-trump? player hand chosen-trump)]
            :when (or forced-play? voluntary-play?)]
        {player chosen-trump}))))

(defn trick
  ; TODO Work in progress...
  "Returns for each player the card that they have chosen to play during the
  trick."
  [players hands trump ruleset]
  (letfn [(play-card [player hand trick-cards]
            (let [card-played (player/play-card player ruleset hand trick-cards trump)]
              (if (rules/legal-card? ruleset card-played hand trick-cards trump)
                {player card-played}
                (throw (Exception. (str player " cheated with " card-played
                                        " and hand: " hand))))))]
    (do (reduce (fn [trick-result player] 
              ; TODO Order is mandatory so map is not suitable
              (let [trick-cards (vals trick-result)]
                (conj trick-result (play-card player (player hands) trick-cards))))
      [] players))))

(defn determine-trick-winner
  ; TODO Maybe use map destructuring for cleaner code.
  "Sorts the trick-result according to the card score and returns the player
  that has the highest card."
  [trick-result trump]
  (key (last
    ; TODO Maybe find a more generic way for sorting cards.
    (sort-by #(cards/card-order (val %) trump) trick-result))))

(defn update-hands
  "Removed the cards from the hands."
  [hands cards-to-remove]
  (for [hand hands]
    (update-in hand (keys hand) #(remove (set cards-to-remove) %))))

;(defn round
;  ; TODO Work in progress...
;  [play-trick players ruleset]
;  (let [hands (deal-cards players (cards/deck))]
;    (let [trump-player (draw-trump hands)]
;      (reduce (fn [updated-hands trick-winner]
;                (play-trick (reorganize 
;      (for 
;      (loop [trick-players players, trick-hands hands, trick-number 1]
;        (let [trick-result (play-trick players hands trump ruleset)]
;          (let [trick-winner (determine-trick-winner trick-result)]
;            (conj trick-result
;                  (recur play-trick 
;                                 (reorganize-players players trick-winner)
;                                 (update-hands hands trick-result))))))
;      )))

(defn play-game
  "Calls the play-round function n (number-of-rounds) times, each time with
  the players in the appropriate order."
  [players ruleset number-of-rounds play-round]
  (for [round-starter (take number-of-rounds (cycle players))]
    (play-round (reorganize-players players round-starter) ruleset)))

