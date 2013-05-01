(ns org.klaverjasaichallenge.ruleset-test
  (:use clojure.test 
        org.klaverjasaichallenge.ruleset
        [org.klaverjasaichallenge.cards :only [card]]))

(defn- test-legality
  [test-data]
  (let [expected-legal-cards (:expected-legal-cards test-data)
        actual-legal-cards (get-legal-cards test-data)]
    (is (= expected-legal-cards actual-legal-cards))))

; Test Amsterdam ruleset
(deftest no-trumping-required-when-mates-card-is-highest
  (test-legality {:ruleset :amsterdam-ruleset
                  :hand-cards [(card :nine :spades) (card :ten :clubs)]
                  :trick-cards [(card :ace :hearts) (card :king :hearts)]
                  :trump :clubs
                  :expected-legal-cards [(card :nine :spades) (card :ten :clubs)]}))

(deftest trumping-required-when-mates-card-is-not-highest
  (test-legality {:ruleset :amsterdam-ruleset
                  :hand-cards [(card :queen :hearts) (card :eight :clubs) (card :queen :diamonds)]
                  :trick-cards [(card :seven :spades) (card :eight :spades) (card :king :spades)]
                  :trump :clubs
                  :expected-legal-cards [(card :eight :clubs)]}))

; Test Rotterdam ruleset
(deftest follows-leading-suit
  (test-legality {:ruleset :rotterdam-ruleset 
                  :hand-cards [(card :nine :hearts) (card :ten :clubs)]
                  :trick-cards [(card :king :hearts)]
                  :trump :clubs
                  :expected-legal-cards [(card :nine :hearts)]}))

(deftest can-trump
  (test-legality {:ruleset :rotterdam-ruleset 
                  :hand-cards [(card :ten :hearts) (card :nine :hearts) (card :king :spades)]
                  :trick-cards [(card :king :clubs)]
                  :trump :hearts
                  :expected-legal-cards [(card :ten :hearts) (card :nine :hearts)]}))

(deftest can-play-high-trump
  (test-legality {:ruleset :rotterdam-ruleset
                  :hand-cards [(card :eight :clubs) (card :jack :clubs)]
                  :trick-cards [(card :king :hearts) (card :nine :clubs)]
                  :trump :clubs
                  :expected-legal-cards [(card :jack :clubs)]}))

(deftest is-allowed-low-trump
  (test-legality {:ruleset :rotterdam-ruleset
                  :hand-cards [(card :eight :clubs) (card :king :spades) (card :ten :clubs)]
                  :trick-cards [(card :king :hearts) (card :nine :clubs)]
                  :trump :clubs
                  :expected-legal-cards [(card :eight :clubs) (card :ten :clubs)]}))

(deftest can-only-play-garbage
  (test-legality {:ruleset :rotterdam-ruleset
                  :hand-cards [(card :eight :clubs) (card :king :diamonds)]
                  :trick-cards [(card :king :hearts) (card :nine :spades)]
                  :trump :spades
                  :expected-legal-cards [(card :eight :clubs) (card :king :diamonds)]}))
