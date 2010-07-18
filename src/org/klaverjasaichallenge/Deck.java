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
		
		// TODO Instead; Loop through the enums, if thats possible?
		
		cards.add(new Card(Suit.CLUBS, Rank.ACE));
		cards.add(new Card(Suit.CLUBS, Rank.KING));
		cards.add(new Card(Suit.CLUBS, Rank.QUEEN));
		cards.add(new Card(Suit.CLUBS, Rank.JACK));
		cards.add(new Card(Suit.CLUBS, Rank.TEN));
		cards.add(new Card(Suit.CLUBS, Rank.NINE));
		cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
		cards.add(new Card(Suit.CLUBS, Rank.SEVEN));
		
		cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
		cards.add(new Card(Suit.DIAMONDS, Rank.KING));
		cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
		cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.NINE));
		cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
		cards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
		
		cards.add(new Card(Suit.HEARTS, Rank.ACE));
		cards.add(new Card(Suit.HEARTS, Rank.KING));
		cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
		cards.add(new Card(Suit.HEARTS, Rank.JACK));
		cards.add(new Card(Suit.HEARTS, Rank.TEN));
		cards.add(new Card(Suit.HEARTS, Rank.NINE));
		cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
		cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
		
		cards.add(new Card(Suit.SPADES, Rank.ACE));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.SPADES, Rank.JACK));
		cards.add(new Card(Suit.SPADES, Rank.TEN));
		cards.add(new Card(Suit.SPADES, Rank.NINE));
		cards.add(new Card(Suit.SPADES, Rank.EIGHT));
		cards.add(new Card(Suit.SPADES, Rank.SEVEN));
		
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
