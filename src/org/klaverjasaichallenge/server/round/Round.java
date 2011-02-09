package org.klaverjasaichallenge.server.round;

import org.klaverjasaichallenge.server.round.action.RoundAction;
import org.klaverjasaichallenge.server.round.action.InitializeRound;
import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.RuleSet;

public class Round {
	private final RoundAction initializeRound;

	public Round(final Table table, final RuleSet ruleSet) {
		this.initializeRound = new InitializeRound(table, ruleSet);
	}

	public void play() {
		RoundAction actionToPerform = this.initializeRound;
		while(actionToPerform != null) {
			actionToPerform = actionToPerform.execute();
		}

		// TODO Return relevant Score data.
	}

	/**
	 * TODO Remove this method
	 */
	public Score getScore(Team team) {
		return null;
	}

	//private static printRoundScore(final RoundResult roundResult) {
		// TODO Fill in...
		//this.logger.debug("--- Round Scores");
		//for (Team team : this.roundScores.keySet()) {
		//	this.logger.debug(team + " scores: " + this.roundScores.get(team) + " points");
		//}
	//}

}
