package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 * TODO This class could do with some documentation about how to use it
 * and why there is a seperation between a serversidetrick and a clienttrick
 * 
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public interface Trick {
	public final static int COUNT = 8;

	public List<Card> getCards();

	public Suit getTrump();

	public Suit getLeadingSuit();

	/**
	 * @return a map with the cards that are played by their associated
	 * player ID's.
	 */
	public Map<Card, Integer> getCardsWithPlayers();
	
	/**
	 * @return the card of the requested player or null if he didn't play
	 * anything.
	 */
	public Card getCardFromPlayer(final int playerId);

	public Card getHighestTrump();

	public Card getHighestCard();

}
