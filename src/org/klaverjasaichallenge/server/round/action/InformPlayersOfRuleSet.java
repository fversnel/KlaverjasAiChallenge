package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.round.RoundResult;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RuleSet;

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
