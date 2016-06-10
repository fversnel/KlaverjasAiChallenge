(ns org.klaverjasaichallenge.ruleset-test
  (:use clojure.test
        org.klaverjasaichallenge.ruleset
        [org.klaverjasaichallenge.cards :only [card cards]]))

;♣♥♦♠

(defn- test-legality
  [test-data]
  (let [expected-legal-cards (:expected-legal-cards test-data)
        actual-legal-cards (legal-cards test-data)]
    (is (= expected-legal-cards actual-legal-cards))))

; Test Amsterdam ruleset
(deftest no-trumping-required-when-mates-card-is-highest
  (test-legality {:ruleset :amsterdam
                  :hand-cards (cards :♠9 :♣10)
                  :trick-cards (cards :♥A :♥K)
                  :trump :♣
                  :expected-legal-cards (cards :♠9 :♣10)}))

(deftest trumping-required-when-mates-card-is-not-highest
  (test-legality {:ruleset :amsterdam
                  :hand-cards (cards :♥Q :♣8 :♦Q)
                  :trick-cards (cards :♠7 :♠8 :♠K)
                  :trump :♣
                  :expected-legal-cards (cards :♣8)}))

; Test Rotterdam ruleset
(deftest follows-leading-suit
  (test-legality {:ruleset :rotterdam
                  :hand-cards (cards :♥9 :♣10)
                  :trick-cards (cards :♥K)
                  :trump :♣
                  :expected-legal-cards (cards :♥9)}))

(deftest can-trump
  (test-legality {:ruleset :rotterdam
                  :hand-cards (cards :♥10 :♥9 :♠K)
                  :trick-cards (cards :♣K)
                  :trump :♥
                  :expected-legal-cards (cards :♥10 :♥9)}))

(deftest can-play-high-trump
  (test-legality {:ruleset :rotterdam
                  :hand-cards (cards :♣8 :♣J)
                  :trick-cards (cards :♥K :♣9)
                  :trump :♣
                  :expected-legal-cards (cards :♣J)}))

(deftest is-allowed-low-trump
  (test-legality {:ruleset :rotterdam
                  :hand-cards (cards :♣8 :♠K :♣10)
                  :trick-cards (cards :♥K :♣9)
                  :trump :♣
                  :expected-legal-cards (cards :♣8 :♣10)}))

(deftest can-only-play-garbage
  (test-legality {:ruleset :rotterdam
                  :hand-cards (cards :♣8 :♦K)
                  :trick-cards (cards :♥K :♠9)
                  :trump :♠
                  :expected-legal-cards (cards :♣8 :♦K)}))
