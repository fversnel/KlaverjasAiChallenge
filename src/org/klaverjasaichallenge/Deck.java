package org.klaverjasaichallenge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 */

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

/**
 * @author Joost Pastoor and Frank Versnel
 *
 */
public class Deck {
	private List<Card> cards;
	
	public Deck() {
		this.cards = this.createDeck();
		this.shuffleDeck(cards);
		
		System.out.println(cards);
	}
	
	/**
	 * TODO this has not been tested yet
	 */
	public Card drawCard() {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
	private List<Card> createDeck() {
		cards = new LinkedList<Card>();
		for (Suit suit : Card.getSuits()) {
			for (Rank rank : Card.getRanks()) {
				cards.add(new Card(suit, rank));
			}
		}
		return cards;
	}

	private void shuffleDeck(List<Card> cards) {
		Collections.shuffle(cards);
	}
}