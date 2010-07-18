/**
 * 
 */
package org.klaverjasaichallenge.shared;

/**
 * @author Joost
 *
 */
public class Card {
	public enum Rank { SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }

    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    
    
    public final Rank rank;
    public final Suit suit;
    
    public Card( Suit suit, Rank rank) {
    	this.suit = suit;
    	this.rank = rank;
    }
    
    public String toString() {
		return rank + "-" + suit;
    }
}
