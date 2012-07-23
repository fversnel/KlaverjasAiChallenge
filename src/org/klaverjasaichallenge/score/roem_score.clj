(ns org.klaverjasaichallenge.score.roem-score
  (:require [org.klaverjasaichallenge.card :as card])
  (:use [clojure.set :only [subset?]]
        [org.klaverjasaichallenge.card :only [card]]))

(def high-ranks #{:ten :jack :queen :king :ace})
(def roem-card-order [:seven :eight :nine :ten :jack :queen :king :ace])

(defn four-high-cards-same-rank
  "Returns 100 (points) if all cards in the trick are of the same rank,
  returns 0 otherwise."
  [trick-cards]
  (let [trick-ranks (map :rank trick-cards)
        all-same-rank? (apply = trick-ranks)
        all-high-rank? (every? high-ranks trick-ranks)]
    (if (and all-high-rank? all-same-rank?) 100 0)))

(defn stuk
  "Returns 20 (points) if the trick contains queen and king of trump,
  returns 0 otherwise."
  [trick-cards trump]
  (if (subset? #{(card :queen trump) (card :king trump)} (set trick-cards))
    20 0))

(defn- consecutive-cards?
  "Returns true if the trick-cards (of the same suit) have a consecutive order
  of length n, returns false otherwise."
  [trick-cards n]
  (let [consecutive-rank-combinations (partition n 1 roem-card-order)
        consecutive-card-combinations 
        (for [suit card/suits, rank-combination consecutive-rank-combinations] 
          (map #(card % suit) rank-combination))]
    (some #(subset? % (set trick-cards)) consecutive-card-combinations)))

(defn three-consecutive-cards
  "Returns 20 (points) when there are three consecutive cards of the same
  suit in the trick, returns 0 otherwise."
  [trick-cards]
  (if (consecutive-cards? trick-cards 3) 20 0))

(defn four-consecutive-cards
  "Returns 30 (points) when there are four consecutive cards of the same
  suit in the trick, returns 0 otherwise."
  [trick-cards]
  (if (consecutive-cards? trick-cards 4) 30 0))

(defn calculate-roem-score [trick-cards trump]
  (+ (four-high-cards-same-rank trick-cards)
     (stuk trick-cards trump)
     (three-consecutive-cards trick-cards)
     (four-consecutive-cards trick-cards)))
