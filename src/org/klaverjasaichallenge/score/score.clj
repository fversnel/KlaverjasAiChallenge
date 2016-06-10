(ns org.klaverjasaichallenge.score.score
  (:require [org.klaverjasaichallenge.score.stock-score :as stock-score]
            [org.klaverjasaichallenge.score.roem-score :as roem-score]))

(def last-trick-points (:last-trick stock-score/points))
(def empty-score {:stock-score 0 :roem-score 0})

(defn calculate-trick-score
  [trick is-last-trick?]
  (let [stock-score (let [s (stock-score/calculate-stock-score trick)]
                      (if is-last-trick? (+ s last-trick-points) s))
        roem-score (roem-score/calculate-roem-score trick)]
    {:stock-score stock-score
     :roem-score roem-score}))

(defn total 
  [{:keys [stock-score roem-score]}]
  (+ stock-score roem-score))

(defn sum-score [s1 s2] (merge-with + s1 s2))
