package org.klaverjasaichallenge.server.round;

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
	 * @return null if card is not in hand, otherwise return the requested
	 * card.
	 */
	public Card drawCard(final Card card) {
		Card cardInHand = null;
		if(this.cards.remove(card)) {
			cardInHand = card;
		}

		return cardInHand;
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
