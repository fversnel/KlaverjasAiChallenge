/**
 * 
 */
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.Map;

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
	
	public boolean giveCards(List<Card> cards);

	/**
	 * @param trump
	 * @return
	 */
	public boolean playOnTrump(Suit trump, Order order);

	/**
	 * @return
	 */
	public Card getCard(Order order, Trick cardsOnTable);
}
