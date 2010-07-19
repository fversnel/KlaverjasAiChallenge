
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;

public class Trick {
	private List<Card> cards;

	public Trick() {
		this.cards = new LinkedList<Card>();
	}

	public Trick(List<Card> cards) {
		this.cards = cards;
	}

	public void addCard(Card card) {
		this.cards.add(card);
	}

	public List<Card> getCards() {
		return this.cards;
	}
}
