(ns org.klaverjasaichallenge.game
  (:require [org.klaverjasaichallenge.cards :as cards]
            [org.klaverjasaichallenge.player :as player]
            [org.klaverjasaichallenge.ruleset :as rules]
            [org.klaverjasaichallenge.score.score :as score])
  (:use [org.klaverjasaichallenge.util :only [re-order find-first]]))

(defn- extract-player-data
  [player-id {:keys [players hands] :as round-data}]
  (merge
    (select-keys round-data [:ruleset :trump :trump-player-id])
    {:player-id player-id
     :hand-cards (get hands player-id)}))

(defn deal-cards
  "Deal each player an equal number of cards from the deck."
  [player-ids deck]
  (let [hand-card-count (int (/ (count deck) (count player-ids)))
        hand-cards (partition hand-card-count deck)]
    (zipmap player-ids hand-cards)))

(defn draw-trump
  [max-draw-count {:keys [players player-order] :as round-data}]
  (let [trumps (shuffle cards/suits)]
    (first
      (for [draw-count (range 0 max-draw-count), player-id player-order
            :let [trump (nth trumps draw-count)
                  player-data (extract-player-data player-id (assoc round-data :trump trump))
                  forced-play? (>= (+ draw-count 1) max-draw-count)
                  voluntary-play? (player/play-trump? (player-id players) player-data)]
            :when (or forced-play? voluntary-play?)]
        {:trump trump :trump-player-id player-id}))))

(defn play-card
  [player {:keys [player-id hand-cards] :as player-data}]
  (let [card-played (player/play-card player player-data)]
    (if (rules/legal-card? card-played player-data)
      (do
        (println (str player-id " played " card-played))
        card-played)
      (throw (Exception. (str [player-id player] " cheated with " card-played
                              " and hand " hand-cards))))))

(def trick-cards (partial map :card))

(defn play-trick
  "Returns for each player the card that they have chosen to play during the
  trick."
  [{:keys [players player-order] :as round-data}]
  (do (reduce (fn [trick player-id]
                (let [player-data (assoc (extract-player-data player-id round-data)
                                         :trick-cards (trick-cards trick))
                      card-played (play-card (player-id players) player-data)]
                  (conj trick {:player-id player-id :card card-played})))
              [] player-order)))

(defn trick-winner
  [trump trick]
  (let [highest-card (cards/highest-card trump (trick-cards trick))]
    (->> trick
      (find-first #(= (:card %) highest-card))
      :player-id)))

(defn update-hands
  "Removes the cards that were played from the hands."
  [cards-played hands]
  (into {} (for [[player-id hand-cards] hands]
             [player-id (remove (set cards-played) hand-cards)])))

(defn play-round
  [initial-round-data]
  (loop [{:keys [player-order tricks] :as round-data} initial-round-data]
    (if (< (count tricks) 8)
      (let [trick (play-trick round-data)
            hands (update-hands (trick-cards trick) (:hands round-data))
            trick-winner-id (trick-winner (:trump round-data) trick)
            new-player-order (re-order player-order
                                       (.indexOf player-order trick-winner-id))]
        (recur (assoc round-data :hands hands :player-order new-player-order
                      :tricks (conj tricks trick))))
      round-data)))

(defn initialize-round
  [{:keys [player-order max-trump-draw-count] :as game-data}]
  (let [round-data (merge game-data
                          {:hands  (deal-cards player-order (cards/deck))
                           :tricks []})
        trump-info (draw-trump max-trump-draw-count round-data)]
    (merge round-data trump-info)))

(defn partner
  [player-order player]
  (let [player-index (.indexOf player-order player)
        partner-index (mod (+ player-index 2) (count player-order))]
    (nth player-order partner-index)))

(defn teams [player-order]
  #{#{(nth player-order 0) (nth player-order 2)}
    #{(nth player-order 1) (nth player-order 3)}})

(defn team [teams player]
  (first (filter #(contains? % player) teams)))

; {(player-id 1) {:total 100 :trump 20 :stock 80},
;  etc...}
(defn score-round [{:keys [player-order trump tricks trump-player-id]
                    :as round-data}]
  (let [teams (teams player-order)
        team (fn [player] (team teams player))
        winning-team (fn [trick] (team (trick-winner trump trick)))
        initial-score (into {} (map vector teams (repeat score/empty-score)))
        trick-scores
        (reduce (fn [score trick]
                  (let [trick-cards (trick-cards trick)
                        is-last-trick? (= trick (last tricks))
                        trick-score (score/calculate-trick-score
                                      {:trump trump :trick-cards trick-cards}
                                      is-last-trick?)
                        winning-team (winning-team trick)]
                    (update-in score [winning-team] (partial score/sum-score trick-score))))
                initial-score tricks)]
    (let [trump-team (team trump-player-id)
          other-team (first (remove #{trump-team} teams))
          trump-team-trick-score (get trick-scores trump-team)
          other-team-trick-score (get trick-scores other-team)
          trump-team-has-highest-score? (> (score/total trump-team-trick-score)
                                           (score/total other-team-trick-score))
          ; TODO Add march bonus
          trump-team-final-score (if trump-team-has-highest-score?
                                   trump-team-trick-score
                                   score/empty-score)
          other-team-final-score (if trump-team-has-highest-score?
                                   other-team-trick-score
                                   (score/sum-score trump-team-trick-score other-team-trick-score))]
      {:trump-team trump-team
       trump-team {:score trump-team-final-score}
       other-team {:score other-team-final-score}})))

(defn initialize-game
  [players ruleset]
  (let [player-ids (range (count players))]
    {:player-order player-ids
     :players (zipmap player-ids players)
     :ruleset ruleset}))

(defn play-game
  "Calls the play-round function n (number-of-rounds) times, each time letting
  another player be the round's starter."
  [ruleset players number-of-rounds]
  (let [game-data (initialize-game players ruleset)
        initial-player-order (:player-order game-data)]
    (for [round-number (range number-of-rounds)]
      (let [new-player-order (re-order initial-player-order round-number)
            initial-round-data (initialize-round game-data)
            round-data (play-round initial-round-data)
            round-score (score-round round-data)]
        {:round-number round-number
         :round round-data
         :score round-score}))))
