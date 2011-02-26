package org.klaverjasaichallenge.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

/**
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
	 * @return the card of the requested player or null if he didn't play
	 * anything.
	 */
	public getCardFromPlayer(final int playerId) {
		return this.serverSideTrick.getCardFromPlayer(playerId);
	}

	public Card getHighestTrump() {
		return this.serverSideTrick.getHighestTrump();
	}

	public Card getHighestCard() {
		return this.serverSideTrick.getHighestCard();
	}

}
