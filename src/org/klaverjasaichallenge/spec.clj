(ns org.klaverjasaichallenge.spec
  (require [clojure.spec :as s]
           [clojure.spec.gen :as gen]
           [org.klaverjasaichallenge.cards :as cards]
           [org.klaverjasaichallenge.ruleset :as rules]
           [org.klaverjasaichallenge.player :as player])
  (use [org.klaverjasaichallenge.util :only [same?]])
  (:import (org.klaverjasaichallenge.player Player)))

(def random-but-legal-ai
  (reify Player
    (play-trump? [_ _] (gen/generate (s/gen boolean?)))
    (play-card [_ player-data]
      (-> player-data
          rules/legal-cards
          set
          s/gen
          gen/generate))))

(defprotocol HasDuplicates
  (has-duplicates? [this] "Returns true iff the collection has duplicates"))

(extend-protocol HasDuplicates
  clojure.lang.Sequential
  (has-duplicates? [s] (not= (count s) (count (distinct s))))
  clojure.lang.IPersistentSet
  (has-duplicates? [_] false)
  clojure.lang.IPersistentMap
  (has-duplicates? [_] false))

(defn max-size
  "Returns true iff the coll is as big or smaller as the given size."
  [size]
  (fn [coll] (<= (count coll) size)))

(defn exact-size
  "Returns true iff the coll is exactly the same size as the given size."
  [size]
  (fn [coll] (= size (count coll))))

(s/def ::card cards/all-cards)
(s/def ::cards (s/and (s/* ::card)
                      #(not (has-duplicates? %))))
(s/def ::complete-deck (s/and ::cards
                              #(= cards/all-cards (set %))))
(s/def ::suit cards/suits)
(s/def ::rank cards/ranks)
(s/def ::trump ::suit)
(s/def ::ruleset (s/with-gen keyword?
                             #(s/gen #{:amsterdam :rotterdam})))
(def player-count 4)
(s/def ::hand-cards (s/and ::cards
                           (max-size 8)))
(s/def ::trick-cards (s/and ::cards
                            (max-size player-count)
                            sequential?))
(s/def ::player (s/with-gen player/player?
                            #(s/gen #{random-but-legal-ai})))
(s/def ::player-id integer?)
(s/def ::trump-player-id ::player-id)
(s/def ::players (s/and (s/map-of ::player-id ::player)
                        (exact-size player-count)))
(s/def ::player-order (s/and (s/* ::player-id)
                             #(not (has-duplicates? %))
                             (exact-size player-count)
                             sequential?))
; TODO Provide custom generator?
(s/def ::hands (s/and (s/map-of ::player-id ::hand-cards)
                      #(s/valid? ::cards (flatten (vals %)))
                      #(same? (map count (vals %)))
                      (exact-size player-count)))


; TODO Generate tricks:
; generate player-ids,
; generate trump,
; generate ruleset
; generate tricks that are legal
(s/def ::trick-entry (s/keys :req-un [::player-id ::card]))
#(s/gen {:trump (gen/generate ::trump)
         :ruleset (gen/generate ::ruleset)
         :tricks })



(s/def ::trick (s/and (s/* ::trick-entry)
                      #(not (has-duplicates? (map :player-id %)))
                      #(s/valid? ::cards (map :card %))
                      (max-size player-count)
                      sequential?))
(s/def ::tricks (s/and (s/* ::trick)
                       (max-size 8)
                       sequential?))


; Datastructure for complete round:
;
; {:players {(player-id 1) team1player1, (player-id 2) team2player1, etc...}
;  :player-order [(player-id 1) (player-id 2) (player-id 3) (player-id 4)]
;  :hands {(player-id 1) #{(card :♥K) (card :♣Q) etc...}
;          etc...}
;  :ruleset :rotterdam
;  :trump :♥
;  :trump-player-id (player-id 1)
;  :tricks [[{:player-id (player-id 1) :card (card :♥K)}
;            {:player-id (player-id 2) :card (card :♥7)}]
;                 etc...]
;
;  }

(defn trick-cards [{:keys [tricks]}]
  (for [trick tricks
        trick-entry trick]
    (:card trick-entry)))

(defn hand-cards [{:keys [hands]}] (-> hands vals flatten))

(s/def ::game
  (s/and
    (s/keys :req-un [::players ::player-order ::hands
                     ::ruleset ::trump ::trump-player-id
                     ::tricks])
    (fn [game]
      (let [player-ids (set (keys (:players game)))]
        (s/and
          #(player-ids (:trump-player-id game))
          ; TODO All players should be in player-order, hands, and tricks
          #(= player-ids (set (:player-order game)))
          #(= player-ids (set (keys (:hands game))))
          #(s/valid? ::complete-deck (concat (trick-cards game) (hand-cards game))))))))

(s/def ::player-data
  (s/and
    (s/keys :req-un [::ruleset ::trump ::trick-cards ::hand-cards])
    #(s/valid? ::cards (concat (:hand-cards %) (:trick-cards %)))))