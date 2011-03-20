package org.klaverjasaichallenge.shared;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.card.Card;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Deck {
	protected final Stack<Card> cards;

	private final Logger logger = Logger.getLogger(getClass());

	public Deck() {
		this.cards = this.createDeck();
		this.shuffleDeck(this.cards);

		this.logger.debug("Deck contains: " + this.cards);
	}

	public Card drawCard() {
		assert(!this.cards.isEmpty()) : "You can't draw cards from an " +
										 "empty deck.";
		return this.cards.pop();
	}

	private Stack<Card> createDeck() {
		final Stack<Card> newDeck = new Stack<Card>();
		for(final Card card : Card.values()) {
			newDeck.add(card);
		}

		return newDeck;
	}

	private void shuffleDeck(final List<Card> cards) {
		Collections.shuffle(cards);
	}
}
