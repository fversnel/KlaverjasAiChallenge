(ns org.klaverjasaichallenge.util)

(defn find-first
  [pred coll]
  (->> coll (filter pred) first))

(defn filter-if
  "Filters the collection based on the predicate if the predicate applies to at least one of
  the elements in the coll, otherwise returns the coll unfiltered."
  [pred coll]
  (let [filtered-coll (filter pred coll)]
    (if (empty? filtered-coll) coll filtered-coll)))

(defn re-order
  "Shifts the position of the items. Keeps the original order of the items in tact.
  For example: (re-order [:a :b :c :d] 2) will return [:c :d :a :b]."
  [coll n]
  (->> coll cycle (drop n) (take (count coll))))
