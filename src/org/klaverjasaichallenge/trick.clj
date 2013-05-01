(ns org.klaverjasaichallenge.trick)

(defn get-leading-suit
  [trick-cards]
  (:suit (first trick-cards)))
