(ns org.klaverjasaichallenge.util)

(defn filter-if
  "Filters the collection based on the predicate if the predicate applies to at least one of
  the elements in the coll, otherwise returns the coll unfiltered."
  [pred coll]
  (if (some pred coll) (filter pred coll) coll))

