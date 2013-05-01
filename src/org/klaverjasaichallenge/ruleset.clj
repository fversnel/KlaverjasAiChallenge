(ns org.klaverjasaichallenge.ruleset
  (:require [org.klaverjasaichallenge.cards :as cards]
            [org.klaverjasaichallenge.trick :as trick])
  (:use [org.klaverjasaichallenge.util :only [filter-if]]))

(defmulti get-legal-cards
  "Filters all the player's hand cards that are legal to the trick."
  (fn [player-data] (:ruleset player-data)))

(defn legal-card? [card player-data]
  (contains? (set (get-legal-cards player-data)) card))

(defn- over-trump-card? 
  "Returns true if the card is higher than the highest trump in the trick-cards, false if not. 
  Also returns false if there is no trump card in the trick."
  [{:keys [trick-cards trump]} card]
  (let [trick-has-trump? (some (partial cards/suit? trump) trick-cards)
        highest-card (cards/get-highest-card trump (conj trick-cards card))]
    (and trick-has-trump? (= highest-card card))))

(defmethod get-legal-cards :rotterdam-ruleset
  [{:keys [hand-cards trick-cards trump] :as player-data}] 
  (if-let [leading-suit (trick/get-leading-suit trick-cards)]
    (->> hand-cards
      (filter-if (partial cards/suit? leading-suit))
      (filter-if (partial cards/suit? trump))
      (filter-if (partial over-trump-card? player-data)))
    hand-cards))
 
(defmethod get-legal-cards :amsterdam-ruleset
  [{:keys [hand-cards trick-cards trump] :as player-data}] 
  (if-let [leading-suit (trick/get-leading-suit trick-cards)]
    (let [partner-plays-highest-card?
          (if-let [partner-card (last (drop-last trick-cards))]
            (= partner-card (cards/get-highest-card trump trick-cards)))]
      (->> hand-cards
        (filter-if (partial cards/suit? leading-suit))
        (filter-if #(and (cards/suit? trump %) 
                         (not partner-plays-highest-card?)))
        (filter-if #(and (over-trump-card? player-data %) 
                         (not partner-plays-highest-card?)))))
    hand-cards))
