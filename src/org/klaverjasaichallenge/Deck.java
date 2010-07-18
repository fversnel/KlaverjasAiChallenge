package org.klaverjasaichallenge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 */

import org.klaverjasaichallenge.shared.Card;
import org.klaverjasaichallenge.shared.Card.Rank;
import org.klaverjasaichallenge.shared.Card.Suit;

/**
 * @author Joost
 *
 */
public class Deck {
	public List<Card> cards;
	
	public Deck() {
		cards = new LinkedList<Card>();

		// Add all cards to the deck
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
		
		Collections.shuffle(cards);
		
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
}
