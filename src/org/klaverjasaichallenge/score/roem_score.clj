(ns org.klaverjasaichallenge.score.roem-score
  (:require [org.klaverjasaichallenge.cards :as cards])
  (:use [clojure.set :only [subset?]]
        [org.klaverjasaichallenge.cards :only [card]]))

(def roem-card-order [:7 :8 :9 :10 :J :Q :K :A])
(def high-cards (set (for [rank #{:10 :J :Q :K :A}
                           suit cards/suits]
                       (card rank suit))))

(defn four-high-cards-same-rank?
  "Returns true iff all trick cards are of the same rank."
  [{:keys [trick-cards]}]
  (contains? high-cards (set trick-cards)))

(defn stuk?
  "Returns true iff the trick cards contain queen and king of trump."
  [{:keys [trump trick-cards]}]
  (subset? #{(card :Q trump) (card :K trump)} (set trick-cards)))

(defn consecutive-cards?
  "Returns true iff the trick cards of the same suit have a consecutive order
  of length n."
  [n {:keys [trick-cards]}]
  (let [consecutive-rank-combinations (partition n 1 roem-card-order)
        consecutive-card-combinations
        (for [suit cards/suits, rank-combination consecutive-rank-combinations]
          (map #(card % suit) rank-combination))]
    (some #(subset? % (set trick-cards)) consecutive-card-combinations)))

(def roem-types
  {:four-high-cards-same-rank {:score-fn four-high-cards-same-rank? :points 100}
   :stuk {:score-fn stuk? :points 20}
   :three-consecutive-cards {:score-fn (partial consecutive-cards? 3) :points 30}
   :four-consecutive-cards {:score-fn (partial consecutive-cards? 4) :points 40}})

(defn calculate-roem-score [{:keys [trump trick-cards] :as trick}]
  (->> (vals roem-types)
    (map (fn [{:keys [score-fn points]}]
           (if (score-fn trick) points 0)))
    (reduce +)))
