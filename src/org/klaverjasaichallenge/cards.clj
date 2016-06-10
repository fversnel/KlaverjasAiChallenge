(ns org.klaverjasaichallenge.cards)

(defrecord Card [rank suit]
  Object
  (toString [_] (str (name suit)
                     (name rank))))

(defmethod print-method Card [card ^java.io.Writer w]
  (.write w (str card)))

(def suits #{:♣ :♥ :♦ :♠})
(def ranks #{:7 :8 :9 :10 :J :Q :K :A})
(defn card
  ([card-expr]
   (let [[suit & rank] (name card-expr)]
     (card (keyword (apply str rank))
           (keyword (str suit)))))
  ([rank suit]
    {:pre [(suits suit) (ranks rank)]}
    (Card. rank suit)))
(defn cards [& cs] (map card cs))
(defn has-suit? [suit card] (= (:suit card) suit))
(defn has-rank? [rank card] (= (:rank card) rank))
(defn card-type [trump card] (if (has-suit? trump card) :trump :normal))
(def all-cards (set (for [suit suits rank ranks] (card rank suit))))

(defn deck [] (shuffle all-cards))

; TODO Rewrite this
(def card-order
  {:normal {:7 0 :8 1 :9 2 :J 3 :Q 4 :K 5 :10 6 :A 7}
   :trump {:7 8 :8 9 :Q 10 :K 11 :10 12 :A 13 :9 14 :J 15}})

(defn sort-cards
  "Sorts cards low to high."
  [trump cards]
  (sort-by (fn [card] (get-in card-order [(card-type trump card) (:rank card)]))
           cards))

(defn highest-card [trump cards]
  (last (sort-cards trump cards)))

(defn to-keyword [card]
  (keyword
    (str (name (:suit card))
         (name (:rank card)))))