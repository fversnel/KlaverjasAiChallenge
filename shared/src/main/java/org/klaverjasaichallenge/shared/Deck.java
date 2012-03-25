package org.klaverjasaichallenge.shared;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.klaverjasaichallenge.shared.card.Card;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Deck {
	protected Stack<Card> cards;

	public Deck() {
		this.cards = this.createDeck();
		Collections.shuffle(cards);
	}

	private Stack<Card> createDeck() {
		final Stack<Card> newDeck = new Stack<Card>();
		for(final Card card : Card.values()) {
			newDeck.add(card);
		}
		return newDeck;
	}

	public Card drawCard() {
		assert(!this.cards.isEmpty()) : "You can't draw cards from an empty deck.";
		return this.cards.pop();
	}

	public boolean isEmpty() {
		return this.cards.isEmpty();
	}

	public List<Card> getCards() {
		return new ArrayList<Card>(cards);
	}
}
