(ns org.klaverjasaichallenge.game-test
  (:use clojure.test 
        org.klaverjasaichallenge.game
        org.klaverjasaichallenge.card
        org.klaverjasaichallenge.player))

(def players [:aap :pipo :beer :mamaloe])

(deftest test-reorganize-players
  (is (= (reorganize-players players :beer)
         [:beer :mamaloe :aap :pipo])))

(deftest test-deal-cards
  (let [hands (deal-cards players (deck))]
    (doseq [player players :let [cards (player hands)]] 
      (is (= (count cards) 8)))))

(defrecord PassTrumpPlayer []
  Player
  (play-trump? [player hand-cards trump] false)
  (play-card [player ruleset hand-cards trick-cards trump] nil))

(defrecord PlayTrumpPlayer []
  Player
  (play-trump? [player hand-cards trump] true)
  (play-card [player ruleset hand-cards trick-cards trump] nil))

(deftest test-draw-trump
  (let [hands {(PassTrumpPlayer.) [], (PlayTrumpPlayer.) []}]
    (println (draw-trump hands))))

(deftest test-determine-trick-winner
  (let [trick-result {:mamaloe (card :seven :hearts)
                      :pipo (card :ace :spades)
                      :aap (card :jack :clubs)
                      :beer (card :nine :clubs)}]
    (is (= (determine-trick-winner trick-result :clubs) :aap))
    (is (= (determine-trick-winner trick-result :spades) :pipo))
    (is (= (determine-trick-winner trick-result :diamonds) :pipo))))


