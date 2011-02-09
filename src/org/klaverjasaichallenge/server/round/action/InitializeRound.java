package org.klaverjasaichallenge.server.round.action;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.shared.RuleSet;

public class InitializeRound extends RoundAction {

	public InitializeRound(final Table table, final RuleSet ruleSet) {
		super(new RoundData(table, ruleSet));
	}

	@Override
	public RoundAction execute() {
		return new InformPlayersRuleSet(this.roundData);
	}

}
