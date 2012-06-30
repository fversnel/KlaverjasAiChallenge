(ns org.klaverjasaichallenge.util)

(defn filter-if
  "Filters the collection based on the predicate if the predicate applies to at least one of
  the elements in the coll, otherwise returns the coll unfiltered."
  [pred coll]
  (if (some pred coll) (filter pred coll) coll))

(defn re-order
  "Drops n items from the infinite version of the coll then returns the coll with its original size.
  For example: (re-order [:a :b :c :d] 2) will return [:c :d :a :b]."
  [coll n]
  (->> coll cycle (drop n) (take (count coll))))
