/**
 * 
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.score.Score;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public interface Player {

	/**
	 * @param cards
	 * @return
	 */
	
	public void giveCards(List<Card> cards);

	/**
	 * @param trump
	 * @return
	 */
	public boolean playOnTrump(Suit trump, Order order);

	/**
	 * @return
	 */
	public Card getCard(Trick trick, Suit trump, Order order);
	
	/**
	 * Returns the trick when it has been played, so the players
	 * know which cards have left the game.
	 * 
	 * @param trick
	 */
	public void endOfTrick(Trick trick);
}
