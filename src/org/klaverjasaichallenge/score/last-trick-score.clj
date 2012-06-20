(ns org.klaverjasaichallenge.score.last-trick-score)

(defn last-trick
  "Returns 10 points if the is-last-trick? predicate is true, returns 0
  otherwise."
  [is-last-trick?]
  (if is-last-trick? 10 0))
