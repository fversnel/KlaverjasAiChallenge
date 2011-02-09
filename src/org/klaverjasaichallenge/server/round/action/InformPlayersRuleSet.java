package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.shared.Player;

class InformPlayersRuleSet extends RoundAction {

	public InformPlayersRuleSet(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		for(final Player player : this.roundData.getPlayers()) {
			player.giveRuleSet(this.roundData.getRuleSet());
		}

		return new DealCards(this.roundData);
	}

}
