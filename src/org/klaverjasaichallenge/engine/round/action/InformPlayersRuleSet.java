package org.klaverjasaichallenge.engine.round.action;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class InformPlayersRuleSet implements RoundAction<Void> {

	private Table table;
	private RuleSet ruleSet;

	public InformPlayersRuleSet(final RuleSet ruleSet, final Table table) {
		this.ruleSet = ruleSet;
		this.table = table;
	}

	@Override
	public Void execute() {
		for(final Player player : this.table) {
			player.notifyRuleset(this.ruleSet);
		}
		
		return null;
	}

}
