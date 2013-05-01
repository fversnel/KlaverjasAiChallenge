(ns org.klaverjasaichallenge.core
  (:require [org.klaverjasaichallenge.game :as game]
            [org.klaverjasaichallenge.player :as player]))

;(defn print-game [game]
;  (doseq [round game] (println "round")
;    (doseq [trick round] (println "trick")
;      (doseq [trick-entry trick] (println trick-entry)))))
;
;(defn -main
;  ""
;  [& args]
;  (do (println "Starting Klaverjas AI Challenge"))
;  (let [players (map #(player/stupid-but-legal-ai %) (range 4))]
;    (print-game (game/play-game :rotterdam-ruleset players 5))))
