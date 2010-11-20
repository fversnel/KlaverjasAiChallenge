package org.klaverjasaichallenge;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

/**
 * @author Joost Pastoor and Frank Versnel
 *
 */
public class Deck {
	private final Stack<Card> cards;

	private final Logger logger;

	public Deck() {
		this.cards = this.createDeck();
		this.shuffleDeck(this.cards);

		this.logger = Logger.getLogger("KlaverjasLogger");
		this.logger.debug(this.cards);
	}

	public Card drawCard() {
		return this.cards.pop();
	}

	private Stack<Card> createDeck() {
		final Stack<Card> cards = new Stack<Card>();
		for(final Suit suit : Card.getSuits()) {
			for(final Rank rank : Card.getRanks()) {
				cards.add(new Card(suit, rank));
			}
		}

		return cards;
	}

	private void shuffleDeck(final List<Card> cards) {
		Collections.shuffle(cards);
	}
}
