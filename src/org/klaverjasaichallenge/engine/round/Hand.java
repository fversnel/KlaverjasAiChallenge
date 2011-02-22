package org.klaverjasaichallenge.engine.round;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;

public class Hand {
	private static final int CARDS_IN_HAND = 8;

	private final List<Card> cards;

	public Hand(final Deck deck) {
		this.cards = this.drawHand(deck);
	}

	/**
	 * @return true if the card was in is hand; false if it was not. If the
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

	private List<Card> drawHand(final Deck deck) {
		List<Card> hand = new LinkedList<Card>();
		for (int i = 1; i <= CARDS_IN_HAND; i++) {
			hand.add(deck.drawCard());
		}

		assert(hand.size() == CARDS_IN_HAND) : "Hand contains " +
				hand.size() + " cards, but should contain "
				+ CARDS_IN_HAND + " cards.";

		return hand;
	}

}
