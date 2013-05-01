(ns org.klaverjasaichallenge.game-test
  (:require [org.klaverjasaichallenge.player :as player]
            [org.klaverjasaichallenge.cards :as cards])
  (:use clojure.test
        org.klaverjasaichallenge.game
        [org.klaverjasaichallenge.cards :only [card]]))

;(def players [:aap :pipo :beer :mamaloe])
;
;(deftest test-deal-cards
;  (let [hands (deal-cards players (cards/deck))]
;    (doseq [hand hands] 
;      (is (= (count (:cards hand)) 8)))))
;
;(defrecord PassTrumpPlayer []
;  player/Player
;  (play-trump? [player hand-cards trump] false)
;  (play-card [player ruleset hand-cards trick-cards trump] nil))
;
;(defrecord PlayTrumpPlayer []
;  player/Player
;  (play-trump? [player hand-cards trump] true)
;  (play-card [player ruleset hand-cards trick-cards trump] nil))
;
;(deftest test-draw-trump
;  (let [players [(PassTrumpPlayer.) (PlayTrumpPlayer.)]
;        hands (for [player players] {:player player :cards []})
;        trump-player (draw-trump hands)]
;    (is (= (PlayTrumpPlayer.) (:player trump-player)))))
;
;(defrecord SimplePlayer []
;  player/Player
;  (play-trump? [player hand-cards trump] true)
;  (play-card [player ruleset hand-cards trick-cards trump] 
;    (first hand-cards)))
;
;(deftest test-play-trick
;  (let [ruleset :rotterdam-ruleset
;        hands [{:player (SimplePlayer.) :cards [(card :seven :hearts)]}]
;        trump :hearts
;        trick-result (play-trick (play-card ruleset trump) hands)]
;    (is (= [{:player (SimplePlayer.) :card (card :seven :hearts)}] trick-result))))
;
;(deftest test-update-hands
;  (let [hands [{:player :some-player 
;                :cards [(card :seven :hearts) (card :nine :clubs)]}
;               {:player :some-other-player 
;                :cards [(card :ten :hearts) (card :king :diamonds)]}]
;        actual-hand (update-hands [(card :seven :hearts) (card :ten :hearts)] hands)]
;    (is (= [(card :nine :clubs)] (:cards (first actual-hand))))
;    (is (= [(card :king :diamonds)] (:cards (second actual-hand))))))
