package org.klaverjasaichallenge.engine.round.action;

import org.klaverjasaichallenge.shared.Deck;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class DealCards extends RoundAction {

	public DealCards(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		final Deck deck = new Deck();

		for(final Player currentPlayer : this.roundData.getTable()) {
			final Hand playersHand = this.dealHand(deck, currentPlayer);
			
			this.roundData.savePlayersHand(currentPlayer, playersHand);
		}

		return new DrawTrump(this.roundData);
	}

	private Hand dealHand(final Deck deck, final Player player) {
		final Hand playersHand = new Hand(deck);
		
		player.receiveCards(playersHand.clone());

		return playersHand;
	}

}
