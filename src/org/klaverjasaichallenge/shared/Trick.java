
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	private static final int FIRST_ADDED_CARD = 1;

	private List<Card> cards;
	private Suit leadingSuit;

	public Trick() {
		this.cards = new LinkedList<Card>();
		this.leadingSuit = null;
	}

	public void addCard(Card card) {
		this.cards.add(card);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = card.getSuit();
		}
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

}
