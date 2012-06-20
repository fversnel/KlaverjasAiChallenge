(ns org.klaverjasaichallenge.rotterdam-ruleset-test
  (:use clojure.test 
        org.klaverjasaichallenge.ruleset
        org.klaverjasaichallenge.card))

(defn- test-legality
  [ruleset testcards]
  (let [expected-legal-cards (:expected-legal-cards testcards)
        actual-legal-cards (get-legal-cards ruleset 
                                            (:hand-cards testcards) 
                                            (:trick-cards testcards)
                                            (:trump testcards))]
    (is (= expected-legal-cards actual-legal-cards))))

(deftest follows-leading-suit
  (test-legality rotterdam-ruleset 
                 {:hand-cards [(card :nine :hearts) (card :ten :clubs)]
                  :trick-cards [(card :king :hearts)]
                  :trump :clubs
                  :expected-legal-cards [(card :nine :hearts)]}))

(deftest can-trump
  (test-legality rotterdam-ruleset 
                 {:hand-cards [(card :ten :hearts) (card :nine :hearts) (card :king :spades)]
                  :trick-cards [(card :king :clubs)]
                  :trump :hearts
                  :expected-legal-cards [(card :ten :hearts) (card :nine :hearts)]}))

(deftest can-play-high-trump
  (test-legality rotterdam-ruleset
                 {:hand-cards [(card :eight :clubs) (card :jack :clubs)]
                  :trick-cards [(card :king :hearts) (card :nine :clubs)]
                  :trump :clubs
                  :expected-legal-cards [(card :jack :clubs)]}))

(deftest is-allowed-low-trump
  (test-legality rotterdam-ruleset
                 {:hand-cards [(card :eight :clubs) (card :king :spades) (card :ten :clubs)]
                  :trick-cards [(card :king :hearts) (card :nine :clubs)]
                  :trump :clubs
                  :expected-legal-cards [(card :eight :clubs) (card :ten :clubs)]}))

(deftest can-only-play-garbage
  (test-legality rotterdam-ruleset
                 {:hand-cards [(card :eight :clubs) (card :king :diamonds)]
                  :trick-cards [(card :king :hearts) (card :nine :spades)]
                  :trump :spades
                  :expected-legal-cards [(card :eight :clubs) (card :king :diamonds)]}))
