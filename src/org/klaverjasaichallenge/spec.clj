(ns org.klaverjasaichallenge.spec
  (require [clojure.spec :as s]
           [org.klaverjasaichallenge.cards :as cards])
  (:import (org.klaverjasaichallenge.player Player)))

(defprotocol HasDuplicates
  (has-duplicates? [this] "Returns true iff the collection has no duplicates"))

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

(defn exact-size [size]
  (fn [coll] (= size (count coll))))

(s/def ::card cards/all-cards)
(s/def ::cards (s/and (s/* ::card)
                      #(not (has-duplicates? %))))
(s/def ::complete-deck (s/and ::cards
                              #(= cards/all-cards %)))
(s/def ::suit cards/suits)
(s/def ::rank cards/ranks)
(s/def ::trump ::suit)
(s/def ::ruleset keyword?)
(def player-count 4)
(s/def ::hand-cards (s/and ::cards
                           (max-size 8)))
(s/def ::trick-cards (s/and sequential?
                            ::cards
                            (max-size player-count)))
(s/def ::player #(satisfies? Player %))
(s/def ::player-id integer?)
(s/def ::trump-player-id ::player-id)
(s/def ::players (s/and (s/map-of ::player-id ::player)
                        (exact-size player-count)))
(s/def ::player-order (s/and sequential?
                             (exact-size player-count)
                             (s/* ::player-id)))
(s/def ::hands (s/and (s/map-of ::player-id ::hand-cards)
                      #(apply = (map count (vals %)))
                      #(s/valid? ::cards (flatten (vals %)))
                      (exact-size player-count)))
(s/def ::trick (s/and sequential?
                      (max-size player-count)
                      (s/* (s/keys :req [::player-id ::card]))))
(s/def ::tricks (s/and sequential?
                       (s/* ::trick)
                       (max-size 8)))


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

; More interesting predicates for game:
; - Number of cards in trick + number of cards in hand == total cards
(s/def ::game (s/keys :req-un [::players ::player-order ::hands
                               ::ruleset ::trump ::trump-player-id
                               ::tricks]))

; TODO Cards in trick cannot be in hand
(s/def ::player-data (s/keys :req-un [::ruleset ::trump ::trick-cards
                                      ::hand-cards]))