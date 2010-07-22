
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	private static final int FIRST_ADDED_CARD = 1;

	private Map<Player, Card> cards;
	private Suit leadingSuit;

	public Trick() {
		this.cards = new HashMap<Player, Card>();
		this.leadingSuit = null;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(player, cardPlayed);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = cardPlayed.getSuit();
		}
	}

	public List<Card> getCards() {
		// TODO Somehow the compiler thinks this is an unsafe operation.
		// Can we fix this?
		return new LinkedList<Card>(this.cards.values());
	}

	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

}
