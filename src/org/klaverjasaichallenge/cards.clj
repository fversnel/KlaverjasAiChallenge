(ns org.klaverjasaichallenge.cards)

(def suits [:♣ :♥ :♦ :♠])
(def ranks [:7 :8 :9 :10 :J :Q :K :A])
(defn card
  ([card-expr]
   (let [[suit & rank] (name card-expr)]
     (card (keyword (apply str rank))
           (keyword (str suit)))))
  ; TODO Order of rank and suit shouldn't matter
  ([rank suit] {:rank rank :suit suit}))
(defn cards [& cs] (map card cs))
(defn has-suit? [suit card] (= (:suit card) suit))
(defn has-rank? [rank card] (= (:rank card) rank))
(defn card-type [trump card] (if (has-suit? trump card) :trump :normal))
(def all-cards (for [suit suits rank ranks] (card rank suit)))

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

(defn to-string [card]
  (str (name (:suit card))
       (name (:rank card))))

(defn to-keyword [card]
  (keyword (to-string card)))