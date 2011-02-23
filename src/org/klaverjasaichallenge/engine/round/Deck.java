package org.klaverjasaichallenge.engine.round;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Deck {
	private static final int TOTAL_NUMBER_OF_CARDS = 32;

	private final Stack<Card> cards;

	private final Logger logger;

	public Deck() {
		this.cards = this.createDeck();
		this.shuffleDeck(this.cards);

		this.logger = Logger.getLogger(Deck.class);
		this.logger.debug(this.cards);
	}

	public Card drawCard() {
		assert(!this.cards.isEmpty()) : "You can't draw cards from an " +
										 "empty deck.";
		return this.cards.pop();
	}

	private Stack<Card> createDeck() {
		final Stack<Card> cards = new Stack<Card>();
		for(final Suit suit : Card.getSuits()) {
			for(final Rank rank : Card.getRanks()) {
				cards.add(new Card(suit, rank));
			}
		}

		assert(cards.size() == TOTAL_NUMBER_OF_CARDS) : "The deck contains " +
				cards.size() + " cards, but should contain "
				+ TOTAL_NUMBER_OF_CARDS + " cards.";

		return cards;
	}

	private void shuffleDeck(final List<Card> cards) {
		Collections.shuffle(cards);
	}
}
