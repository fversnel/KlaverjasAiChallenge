/**
 *
 */
package org.klaverjasaichallenge.shared.card;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

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

	public boolean isHigherThan(final Trick trick, final Card card) {
		return this.isHigherThan(trick.getTrump(), trick.getLeadingSuit(), card);
	}

	/**
	 * @deprecated isHigherThan() is never valid outside a given Trick.
	 * Therefore, do not pass the trump and the leading suit separately into
	 * this method but use isHigherThan(trump, card) instead.
	 */
	@Deprecated
	public boolean isHigherThan(final Suit trump, final Suit leadingSuit, final Card card) {
		Boolean result = null;

		final Suit cardSuit = card.getSuit();

		// If both are trumps
		if(this.suit.equals(trump) && cardSuit.equals(trump)) {
			result = this.getTrumpOrder().isHigherThan(card.getTrumpOrder());
		}
		// If this card is a trump, but the card to compare it with is not
		else if(this.suit.equals(trump) && !cardSuit.equals(trump)) {
			result = true;
		}
		// If this card is not a trump, but the card to compare it with is
		else if(!this.suit.equals(trump) && cardSuit.equals(trump)) {
			result = false;
		}
		// If both cards are of the leading suit
		else if(this.suit.equals(leadingSuit) && cardSuit.equals(leadingSuit)) {
			result = this.getNormalOrder().isHigherThan(card.getNormalOrder());
		}
		// If this card is a leadingSuit, but the card to compare it with is not
		else if(this.suit.equals(leadingSuit) && !cardSuit.equals(leadingSuit)) {
			result = true;
		}
		// If this card is not a leadingSuit, but the card to compare it with is
		else if(!this.suit.equals(leadingSuit) && cardSuit.equals(leadingSuit)) {
			result = false;
		}
		// If both cards are not of leading suit comparing doesn't matter
		// anymore so it will return false.
		else if (!this.suit.equals(leadingSuit) && !cardSuit.equals(leadingSuit)) {
			result = false;
		}

		assert(result != null);

		return result;
	}

	public boolean equals(final Card toCompare) {
		return this.suit.equals(toCompare.getSuit()) &&
				this.rank.equals(toCompare.getRank());
	}

	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}

	/**
	 * @deprecated use {@link Trick#getHighestCard} instead.
	 */
	@Deprecated
	public static Card max(final Suit trump, final Suit leadingSuit, final List<Card> cards) {
		assert(!cards.isEmpty()) : "Cannot determine the maximum on a stack of 0 cards.";

		Card highestCard = cards.get(0);

		for(Card card : cards) {
			if(card.isHigherThan(trump, leadingSuit, highestCard)) {
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
			Suit cardSuit = card.getSuit();
			if((highestCard == null && cardSuit.equals(trump)) ||
					(cardSuit.equals(trump) &&
					 card.getTrumpOrder().isHigherThan(highestCard.getTrumpOrder()))) {
				highestCard = card;
			}
		}

		return highestCard;
	}

	/**
	 * TODO Introduce the concept of Hand in the shared package, so this
	 * helper method can be put in that class, which is much more
	 * appropriate than as a helper method in Card.
	 */
	public static boolean hasSuit(final Suit suit, final List<Card> cards) {
		boolean check = false;

		for(Card card : cards) {
			if(card.getSuit().equals(suit)) {
				check = true;
			}
		}

		return check;
	}

	public static List<Suit> getSuits() {
		final List<Suit> suits = new LinkedList<Suit>();
		suits.addAll(Arrays.asList(Suit.values()));

		return suits;
	}
	
	public static List<Rank> getRanks() {
		final List<Rank> ranks = new LinkedList<Rank>();
		ranks.addAll(Arrays.asList(Rank.values()));

		return ranks;
	}	

}
