(ns org.klaverjasaichallenge.core
  (:require [org.klaverjasaichallenge.game :as game]
            [org.klaverjasaichallenge.player :as player]))

(defn -main
  ""
  [& args]
  (do (println "Starting Klaverjas AI Challenge"))
  (let [players (map #(player/stupid-but-legal-ai %) (range 4))]
    (game/play-game :rotterdam-ruleset players 5)))
