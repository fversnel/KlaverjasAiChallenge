/**
 * 
 */
package org.klaverjasaichallenge.shared.card;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.card.rank.*;
import org.klaverjasaichallenge.shared.card.suit.*;

/**
 * @author Joost
 *
 */
public class Card {
	private final Suit suit;
	private final Rank rank;

	public Card(final Suit suit, final Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Points getPoints(final Suit trump) {
		Points points = new Points();

		if(this.suit.equals(trump)) {
			points = this.rank.getTrumpPoints();
		} else {
			points = this.rank.getNormalPoints();
		}

		return points;
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

	public boolean isHigherThan(final Suit trump, final Card card) {
		boolean result = false;

		if(this.suit.equals(trump) && card.getSuit().equals(trump)) {
			result = this.getTrumpOrder().isHigherThan(card.getTrumpOrder());
		} else if(this.suit.equals(trump) && !card.getSuit().equals(trump)) {
			result = true;
		} else if(!this.suit.equals(trump) && card.getSuit().equals(trump)) {
			result = false;
		} else if(!this.suit.equals(trump) && !card.getSuit().equals(trump)) {
			result = this.getNormalOrder().isHigherThan(card.getNormalOrder());
		}

		return result;
	}

	public String toString() {
		return this.rank + " of " + this.suit;
	}

	public static Card max(final Suit trump, final List<Card> cards) {
		Card highestCard = null;
		for(Card card : cards) {
			List<Card> otherCards = new LinkedList<Card>(cards);
			otherCards.remove(card);
			for(Card cardToCompare : toCompare) {
				if(card.isHigherThan(cardToCompare)) {
					highestCard = card;
				}
			}
		}
		return highestCard;
	}

	public static List<Suit> getSuits() {
		List<Suit> suits = new ArrayList<Suit>();
		suits.add(new Clubs());
		suits.add(new Diamonds());
		suits.add(new Hearts());
		suits.add(new Spades());

		return suits;
	}

	public static List<Rank> getRanks() {
		List<Rank> ranks = new ArrayList<Rank>();
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
