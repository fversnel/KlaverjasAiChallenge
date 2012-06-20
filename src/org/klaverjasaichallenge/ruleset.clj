(ns org.klaverjasaichallenge.ruleset
  (:require [org.klaverjasaichallenge.card :as cards])
  (:use org.klaverjasaichallenge.util))

(defprotocol Ruleset
  (get-legal-cards 
    [ruleset hand-cards trick-cards trump] "Filters all hand cards that are
                                           legal to the trick."))

(defn legal-card?
  [ruleset card hand-cards trick-cards trump] 
  (let [legal-cards (get-legal-cards ruleset hand-cards trick-cards trump)]
    (some (set legal-cards) card)))

(defrecord RotterdamRuleset []
  Ruleset
  (get-legal-cards 
    [ruleset hand-cards trick-cards trump] 
    (if-let [leading-suit (:suit (first trick-cards))]
      (->> hand-cards
        (filter-if #(cards/suit? leading-suit %))
        (filter-if #(cards/suit? trump %))
        ; Make sure the player has to overtrump if he can.
        (filter-if #(cards/high-trump-card? trick-cards trump %)))
      ; If no leading suit is present any card is a legal card
      hand-cards)))

(def rotterdam-ruleset (RotterdamRuleset.))

