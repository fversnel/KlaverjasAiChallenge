/**
 *
 */
package org.klaverjasaichallenge.shared.card;

import java.util.List;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public enum Card {
	SEVEN_OF_SPADES(Rank.SEVEN, Suit.SPADES),
	EIGHT_OF_SPADES(Rank.EIGHT, Suit.SPADES),
	NINE_OF_SPADES(Rank.NINE, Suit.SPADES),
	TEN_OF_SPADES(Rank.TEN, Suit.SPADES),
	JACK_OF_SPADES(Rank.JACK, Suit.SPADES),
	QUEEN_OF_SPADES(Rank.QUEEN, Suit.SPADES),
	KING_OF_SPADES(Rank.KING, Suit.SPADES),
	ACE_OF_SPADES(Rank.ACE, Suit.SPADES),

	SEVEN_OF_CLUBS(Rank.SEVEN, Suit.CLUBS),
	EIGHT_OF_CLUBS(Rank.EIGHT, Suit.CLUBS),
	NINE_OF_CLUBS(Rank.NINE, Suit.CLUBS),
	TEN_OF_CLUBS(Rank.TEN, Suit.CLUBS),
	JACK_OF_CLUBS(Rank.JACK, Suit.CLUBS),
	QUEEN_OF_CLUBS(Rank.QUEEN, Suit.CLUBS),
	KING_OF_CLUBS(Rank.KING, Suit.CLUBS),
	ACE_OF_CLUBS(Rank.ACE, Suit.CLUBS),

	SEVEN_OF_HEARTS(Rank.SEVEN, Suit.HEARTS),
	EIGHT_OF_HEARTS(Rank.EIGHT, Suit.HEARTS),
	NINE_OF_HEARTS(Rank.NINE, Suit.HEARTS),
	TEN_OF_HEARTS(Rank.TEN, Suit.HEARTS),
	JACK_OF_HEARTS(Rank.JACK, Suit.HEARTS),
	QUEEN_OF_HEARTS(Rank.QUEEN, Suit.HEARTS),
	KING_OF_HEARTS(Rank.KING, Suit.HEARTS),
	ACE_OF_HEARTS(Rank.ACE, Suit.HEARTS),

	SEVEN_OF_DIAMONDS(Rank.SEVEN, Suit.DIAMONDS),
	EIGHT_OF_DIAMONDS(Rank.EIGHT, Suit.DIAMONDS),
	NINE_OF_DIAMONDS(Rank.NINE, Suit.DIAMONDS),
	TEN_OF_DIAMONDS(Rank.TEN, Suit.DIAMONDS),
	JACK_OF_DIAMONDS(Rank.JACK, Suit.DIAMONDS),
	QUEEN_OF_DIAMONDS(Rank.QUEEN, Suit.DIAMONDS),
	KING_OF_DIAMONDS(Rank.KING, Suit.DIAMONDS),
	ACE_OF_DIAMONDS(Rank.ACE, Suit.DIAMONDS);

	private final Rank rank;
	private final Suit suit;

	private Card(final Rank rank, final Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Points getPoints(final Suit trump) {
		if(this.suit.equals(trump)) {
			return this.rank.getTrumpPoints();
		}

		return this.rank.getNormalPoints();
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}

	public Order getNormalOrder() {
		return this.rank.getNormalOrder();
	}

	public Order getTrumpOrder() {
		return this.rank.getTrumpOrder();
	}

	public Order getRoemOrder() {
		return this.rank.getRoemOrder();
	}

	public boolean isHigherThan(final Trick trick, final Card cardToCompare) {
		Boolean result = null;

		final Suit leadingSuit = trick.getLeadingSuit();
		final Suit trump = trick.getTrump();

		if(this.isOfSuit(trump) && cardToCompare.isOfSuit(trump)) {
			result = this.getTrumpOrder().isHigherThan(cardToCompare.getTrumpOrder());
		} else if(this.isOfSuit(trump) && !cardToCompare.isOfSuit(trump)) {
			result = true;
		} else if(!this.isOfSuit(trump) && cardToCompare.isOfSuit(trump)) {
			result = false;
		} else if(this.isOfSuit(leadingSuit) && cardToCompare.isOfSuit(leadingSuit)) {
			result = this.getNormalOrder().isHigherThan(cardToCompare.getNormalOrder());
		} else if(this.isOfSuit(leadingSuit) && !cardToCompare.isOfSuit(leadingSuit)) {
			result = true;
		} else if(!this.isOfSuit(leadingSuit) && cardToCompare.isOfSuit(leadingSuit)) {
			result = false;
		} else if (!this.isOfSuit(leadingSuit) && !cardToCompare.isOfSuit(leadingSuit)) {
			result = false;
		}

		assert(result != null);

		return result;
	}
	
	public boolean isOfSuit(final Suit suitToCompare) {
		return this.suit.equals(suitToCompare);
	}

	public boolean hasSameSuitAs(final Card cardToCompare) {
		return this.suit.equals(cardToCompare.getSuit());
	}
	
	public boolean isOfRank(final Rank rankToCompare) {
		return this.rank.equals(rankToCompare);
	}
	
	public boolean hasSameRankAs(final Card cardToCompare) {
		return this.rank.equals(cardToCompare.getRank());
	}

	public boolean equals(final Card cardToCompare) {
		return this == cardToCompare;
	}

	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}

	public static Card max(final Trick trick, final List<Card> cards) {
		assert(!cards.isEmpty()) : "Cannot determine the maximum on a stack of 0 cards.";

		Card highestCard = cards.get(0);

		for(Card card : cards) {
			if(card.isHigherThan(trick, highestCard)) {
				highestCard = card;
			}
		}

		return highestCard;
	}

	/**
	 * @return returns null if there is no trump.
	 */
	public static Card highestTrump(final Suit trump, final List<Card> cards) {
		Card highestCard = null;

		for(Card card : cards) {
			if(card.isOfSuit(trump)) {
				if(highestCard == null) {
					highestCard = card;
				} else if(card.getTrumpOrder().isHigherThan(highestCard.getTrumpOrder())) {
					highestCard = card;
				}
			}
		}

		return highestCard;
	}

	public static boolean hasSuit(final Suit suit, final List<Card> cards) {
		boolean check = false;

		for(Card card : cards) {
			if(card.isOfSuit(suit)) {
				check = true;
			}
		}

		return check;
	}

}
