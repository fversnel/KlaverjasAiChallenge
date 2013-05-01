(ns org.klaverjasaichallenge.trick-test
  (:use clojure.test 
        org.klaverjasaichallenge.trick
        [org.klaverjasaichallenge.cards :only [card]]))

(deftest test-get-mates-card
  (let [mates-card (get-mates-card [(card :queen :diamonds) (card :eight :spades)
                                    (card :queen :spades) (card :nine :spades)] 
                                   (card :nine :spades))]
    (is (= (card :eight :spades) mates-card))))
