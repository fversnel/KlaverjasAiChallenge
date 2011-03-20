package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Hand {
	protected static final int CARDS_IN_HAND = 8;

	protected final List<Card> cards;

	public Hand(final Deck deck) {
		this.cards = this.drawHand(deck);
	}

	public Hand(final List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * @return true if the card was in his hand; false if it was not. If the
	 * card was in his hand, it has been removed.
	 */
	public boolean drawCard(final Card card) {
		return this.cards.remove(card);
	}

	public int getNumberOfCards() {
		return this.cards.size();
	}

	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards);
	}

	public boolean hasSuit(final Suit suit) {
		return Card.hasSuit(suit, this.cards);
	}

	public Card highestTrump(final Trick trick) {
		return Card.highestTrump(trick.getTrump(), this.cards);
	}

	@Override
	public Hand clone() {
		return new Hand(new LinkedList<Card>(this.cards));
	}

	private List<Card> drawHand(final Deck deck) {
		List<Card> hand = new LinkedList<Card>();
		for (int i = 1; i <= CARDS_IN_HAND; i++) {
			hand.add(deck.drawCard());
		}

		return hand;
	}

}
