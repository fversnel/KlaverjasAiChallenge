(ns org.klaverjasaichallenge.player
  (:require [org.klaverjasaichallenge.ruleset :as rules]))

(defprotocol Player
  "The player in a game of klaverjas, can either be a human or an AI."
  (play-trump? [player player-data] "Whether the player plays on the given
                                     trump or not.")
  (play-card [player player-data]))

(defn player? [x] (satisfies? Player x))

(def stupid-but-legal-ai
  (reify Player
    (play-trump? [_ _] true)
    (play-card [_ player-data] (first (rules/legal-cards player-data)))))
