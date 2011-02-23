package org.klaverjasaichallenge.engine.round.action;

import org.klaverjasaichallenge.shared.Player;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class InformPlayersRuleSet extends RoundAction {

	public InformPlayersRuleSet(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		for(final Player player : this.roundData.getTable()) {
			player.giveRuleSet(this.roundData.getRuleSet());
		}

		return new DealCards(this.roundData);
	}

}
