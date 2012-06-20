(ns org.klaverjasaichallenge.ruleset
  (:use (org.klaverjasaichallenge.card)))

(defn leading-suit
  [trick-cards]
  (:suit (first trick-cards)))

