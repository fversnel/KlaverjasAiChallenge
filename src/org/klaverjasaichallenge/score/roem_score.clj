(ns org.klaverjasaichallenge.score.roem-score
  (:use [clojure.set :only [subset?]] 
         org.klaverjasaichallenge.card))

(def high-ranks #{:ten :jack :queen :king :ace})
(def roem-card-order [:seven :eight :nine :ten :jack :queen :king :ace])

(defn four-high-cards-same-rank
  "Returns 100 (points) if all cards in the trick are of the same rank,
  returns 0 otherwise."
  [trick-cards]
  (let [trick-ranks (map :rank trick-cards)
        all-same-rank? (every? #(= % (first trick-ranks)) trick-ranks)
        all-high-rank? (every? high-ranks trick-ranks)]
    (if (and all-high-rank? all-same-rank?) 100 0)))

(defn stuk
  "Returns 20 (points) if the trick contains queen and king of trump,
  returns 0 otherwise."
  [trick-cards trump]
  (if (subset? #{(card :queen trump) (card :king trump)} (set trick-cards))
    20 0))

(defn- consecutive-cards?
  "Returns true if the trick-cards (of the same suit) have a consecutive order
  of n length, returns false otherwise."
  [trick-cards n]
  ; Partition will make all possible combinations of consecutive cards
  ; according to the roem-card-order, n specifies the size of the
  ; partitions.
  (let [consecutive-rank-combinations (partition n 1 roem-card-order)]
    (some true?
      ; Filter cards based on suit
      (for [suit suits :let [cards-of-same-suit (filter #(suit? suit %) trick-cards)
                             card-ranks (map :rank cards-of-same-suit)]]
        ; Test if the given set of cards exists in at least one of
        ; consecutive card combinations.
        (some #(subset? % (set card-ranks)) consecutive-rank-combinations)))))

(defn three-consecutive-cards
  "Returns 20 (points) when there are three consecutive cards of the same
  suit in the trick, returns 0 otherwise."
  [trick-cards]
  (if (consecutive-cards? trick-cards 3) 20 0))

(defn four-consecutive-cards
  "Returns 30 (points) when there are four consecutive cards of the same
  suit in the trick, returns 0 otherwise."
  [trick-cards]
  (if (consecutive-cards? trick-cards 4) 30 0))

(defn calculate-roem-score [trick-cards trump]
  (+ (four-high-cards-same-rank trick-cards)
     (stuk trick-cards trump)
     (three-consecutive-cards trick-cards)
     (four-consecutive-cards trick-cards)))
