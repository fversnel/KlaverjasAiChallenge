(ns org.klaverjasaichallenge.player
  (:require [org.klaverjasaichallenge.ruleset :as rules]))

(defprotocol Player
  "The player in a game of klaverjas, can either be a human or an AI."
  (play-trump? [player player-data] "Whether the player plays on the given
                                     trump or not.")
  (play-card [player player-data]))

(defrecord StupidButLegalAi []
  Player
  (play-trump? [player player-data] true)
  (play-card [player player-data] 
    (let [legal-cards (rules/get-legal-cards player-data)]
      (first legal-cards))))

(defn stupid-but-legal-ai [] (StupidButLegalAi.))
