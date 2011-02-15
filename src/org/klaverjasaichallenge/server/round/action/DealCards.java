package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.server.round.Deck;
import org.klaverjasaichallenge.server.round.Hand;
import org.klaverjasaichallenge.shared.Player;

public class DealCards extends RoundAction {

	public DealCards(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		final Deck deck = new Deck();

		for(final Player currentPlayer : this.roundData.getTable()) {
			this.roundData.putPlayersHand(currentPlayer,
					this.dealHand(deck, currentPlayer));
		}

		return new DrawTrump(this.roundData);
	}

	private Hand dealHand(final Deck deck, final Player player) {
		final Hand playersHand = new Hand(deck);
		player.giveCards(playersHand.getCards());

		return playersHand;
	}

}
