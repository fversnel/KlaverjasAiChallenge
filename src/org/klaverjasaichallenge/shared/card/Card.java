/**
 *
 */
package org.klaverjasaichallenge.shared.card;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.rank.*;
import org.klaverjasaichallenge.shared.card.suit.*;

/**
 * TODO Make Suit and Rank enums.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Card {
	private final Suit suit;
	private final Rank rank;

	public Card(final Suit suit, final Rank rank) {
		this.suit = suit;
		this.rank = rank;
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
		return this.getSuit().equals(toCompare.getSuit())
				&& this.getRank().equals(toCompare.getRank());
	}

	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}

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
		suits.add(new Clubs());
		suits.add(new Diamonds());
		suits.add(new Hearts());
		suits.add(new Spades());

		return suits;
	}

	public static List<Rank> getRanks() {
		final List<Rank> ranks = new LinkedList<Rank>();
		ranks.add(new Seven());
		ranks.add(new Eight());
		ranks.add(new Nine());
		ranks.add(new Ten());
		ranks.add(new Jack());
		ranks.add(new Queen());
		ranks.add(new King());
		ranks.add(new Ace());

		return ranks;
	}

}
