(ns org.klaverjasaichallenge.player
  (:require [org.klaverjasaichallenge.ruleset :as ruleset]))

(defprotocol Player
  "The player in a game of klaverjas, can either be a human or an AI."
  (play-trump? [player hand-cards trump] "Whether the player plays on the given
                                          trump or not.")
  (play-card [player ruleset hand-cards trick-cards trump]))

(defrecord StupidButLegalAi [id]
  Player
  (play-trump? [player hand-cards trump] true)
  (play-card [player ruleset hand-cards trick-cards trump] 
    (let [legal-cards (ruleset/get-legal-cards ruleset hand-cards trick-cards trump)]
      (first legal-cards))))

(defn stupid-but-legal-ai [id]
  (StupidButLegalAi. id))
