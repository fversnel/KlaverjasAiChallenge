package org.klaverjasaichallenge;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
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
	private Stack<Card> cards;
	
	public Deck() {
		this.cards = this.createDeck();
		this.shuffleDeck(cards);
		
		System.out.println(cards);
	}
	
	/**
	 * TODO this has not been tested yet
	 */
	public Card drawCard() {
		return cards.pop();
	}
	
	private Stack<Card> createDeck() {
		cards = new Stack<Card>();
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