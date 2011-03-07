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
public class Trick {
	public final static int COUNT =
		org.klaverjasaichallenge.engine.round.Trick.COUNT;

	private org.klaverjasaichallenge.engine.round.Trick serverSideTrick;

	public Trick(org.klaverjasaichallenge.engine.round.Trick serverSideTrick) {
		this.serverSideTrick = serverSideTrick;
	}

	public List<Card> getCards() {
		return this.serverSideTrick.getCards();
	}

	public Suit getTrump() {
		return this.serverSideTrick.getTrump();
	}

	public Suit getLeadingSuit() {
		return this.serverSideTrick.getLeadingSuit();
	}

	/**
	 * @return a map with the cards that are played by their associated
	 * player ID's.
	 */
	public Map<Card, Integer> getCardsWithPlayers() {
		return this.serverSideTrick.getCardsWithPlayersIds();
	}	
	
	/**
	 * @return the card of the requested player or null if he didn't play
	 * anything.
	 */
	public Card getCardFromPlayer(final int playerId) {
		return this.serverSideTrick.getCardFromPlayer(playerId);
	}

	public Card getHighestTrump() {
		return this.serverSideTrick.getHighestTrump();
	}

	public Card getHighestCard() {
		return this.serverSideTrick.getHighestCard();
	}

}
