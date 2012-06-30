(ns org.klaverjasaichallenge.game-test
  (:use clojure.test 
        org.klaverjasaichallenge.game
        org.klaverjasaichallenge.card
        org.klaverjasaichallenge.ruleset
        org.klaverjasaichallenge.player))

(def players [:aap :pipo :beer :mamaloe])

(deftest test-deal-cards
  (let [hands (deal-cards players (deck))]
    (doseq [hand hands] 
      (is (= (count (:cards hand)) 8)))))

(defrecord PassTrumpPlayer []
  Player
  (play-trump? [player hand-cards trump] false)
  (play-card [player ruleset hand-cards trick-cards trump] nil))

(defrecord PlayTrumpPlayer []
  Player
  (play-trump? [player hand-cards trump] true)
  (play-card [player ruleset hand-cards trick-cards trump] nil))

(deftest test-draw-trump
  (let [players [(PassTrumpPlayer.) (PlayTrumpPlayer.)]
        hands (for [player players] {:player player :cards []})
        trump-player (draw-trump hands)]
    (is (= (PlayTrumpPlayer.) (:player trump-player)))))

(defrecord SimplePlayer []
  Player
  (play-trump? [player hand-cards trump] true)
  (play-card [player ruleset hand-cards trick-cards trump] 
    (first hand-cards)))

(deftest test-play-trick
  (let [ruleset :rotterdam-ruleset
        hands [{:player (SimplePlayer.) :cards [(card :seven :hearts)]}]
        trump :hearts
        trick-result (play-trick ruleset hands trump)]
    (is (= [{:player (SimplePlayer.) :card (card :seven :hearts)}] trick-result))))

;(deftest test-determine-trick-winner
;  (let [trick-result [{:player :mamaloe :card (card :seven :hearts)}
;                      {:player :pipo :card (card :ace :spades)}
;                      {:player :aap :card (card :jack :clubs)}
;                      {:player :beer :card (card :nine :clubs)}]]
;    (is (= (get-trick-winner trick-result :clubs) :aap))
;    (is (= (get-trick-winner trick-result :spades) :pipo))
;    (is (= (get-trick-winner trick-result :diamonds) :pipo))))

(deftest test-update-hands
  (let [hands [{:player :some-player 
                :cards [(card :seven :hearts) (card :nine :clubs)]}
               {:player :some-other-player 
                :cards [(card :ten :hearts) (card :king :diamonds)]}]
        actual-hand (update-hands hands [(card :seven :hearts) (card :ten :hearts)])]
    (is (= [(card :nine :clubs)] (:cards (first actual-hand))))
    (is (= [(card :king :diamonds)] (:cards (second actual-hand))))))


