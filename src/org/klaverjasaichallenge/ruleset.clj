(ns org.klaverjasaichallenge.ruleset
  (:require [org.klaverjasaichallenge.card :as cards]
            [org.klaverjasaichallenge.trick :as trick])
  (:use [org.klaverjasaichallenge.util :only [filter-if]]))

(defmulti get-legal-cards 
  "Filters all hand cards that are legal to the trick."
  (fn [ruleset hand-cards trick-cards trump] ruleset))

(defn legal-card?
  [ruleset card hand-cards trick-cards trump] 
  (let [legal-cards (get-legal-cards ruleset hand-cards trick-cards trump)]
    (contains? (set legal-cards) card)))

(defn- over-trump-card? 
  "Returns true if the card is higher than the highest trump in the trick-cards, false if not. 
  Also returns false if there is no trump card in the trick."
  [trick-cards trump card]
  (let [trick-has-trump? (some #(cards/suit? trump %) trick-cards)
        highest-card (cards/get-highest-card trump (conj trick-cards card))]
    (and trick-has-trump? (= highest-card card))))

(defn- mate-plays-highest-card?
  [trick-cards trump]
  (if-let [mates-card (trick/get-mates-card trick-cards)]
    (= mates-card (cards/get-highest-card trump trick-cards))
    false))

(defmethod get-legal-cards :amsterdam-ruleset
  [ruleset hand-cards trick-cards trump] 
  (if-let [leading-suit (trick/get-leading-suit trick-cards)]
    (let [mate-plays-highest-card? (mate-plays-highest-card? trick-cards trump)]
      (->> hand-cards
        (filter-if #(cards/suit? leading-suit %))
        (filter-if #(and (cards/suit? trump %) 
                         (not mate-plays-highest-card?)))
        (filter-if #(and (over-trump-card? trick-cards trump %) 
                         (not mate-plays-highest-card?)))))
    hand-cards))

(defmethod get-legal-cards :rotterdam-ruleset
  [ruleset hand-cards trick-cards trump] 
  (if-let [leading-suit (trick/get-leading-suit trick-cards)]
    (->> hand-cards
      (filter-if #(cards/suit? leading-suit %))
      (filter-if #(cards/suit? trump %))
      (filter-if #(over-trump-card? trick-cards trump %)))
    hand-cards))
