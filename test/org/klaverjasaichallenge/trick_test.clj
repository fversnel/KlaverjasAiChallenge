(ns org.klaverjasaichallenge.trick-test
  (:use clojure.test 
        org.klaverjasaichallenge.card
        org.klaverjasaichallenge.trick))

(deftest test-get-mates-card
  (let [mates-card (get-mates-card [(card :queen :diamonds) (card :eight :spades)])]
    (is (= (card :queen :diamonds) mates-card))))
