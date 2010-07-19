/**
 * 
 */
package org.klaverjasaichallenge.shared.card;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.rank.*;
import org.klaverjasaichallenge.shared.card.suit.*;

/**
 * @author Joost
 *
 */
public class Card {
	private static final Suit[] SUITS = {new Clubs(), new Diamonds(), new Hearts(), new Spades()};
	private static final Rank[] RANKS = {new Seven(), new Eight(), new Nine(), new Ten(), new Jack(), 
		new Queen(), new King(), new Ace()};
	
	private final Suit suit;
	private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
    	this.suit = suit;
    	this.rank = rank;
    }

	public Points getPoints(Suit trump) {
		if(this.suit == trump) {
			return this.rank.getTrumpPoints();
		} else {
			return this.rank.getNormalPoints();
		}
	}
    
    public String toString() {
		return this.rank + " of " + this.suit;
    }
    
    public static Suit[] getSuits() {
    	return SUITS;
    }
    
    public static Rank[] getRanks() {
    	return RANKS;
    }
}
