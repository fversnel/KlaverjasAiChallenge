package org.klaverjasaichallenge.server.round;

import java.util.Map;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.server.round.action.RoundAction;
import org.klaverjasaichallenge.server.round.action.RoundData;
import org.klaverjasaichallenge.server.round.action.InformPlayersRuleSet;
import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.RuleSet;

public class Round {
	private final RoundData roundData;

	private final Logger logger;

	public Round(final Table table, final RuleSet ruleSet) {
		this.roundData = this.initializeRound(table, ruleSet);

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	public void play() {
		RoundAction actionToPerform = new InformPlayersRuleSet(this.roundData);
		while(actionToPerform != null) {
			actionToPerform = actionToPerform.execute();
		}
		this.printRoundScores(this.roundData.getRoundScores());
	}

	public Score getScore(final Team team) {
		Map<Team, Score> roundScores = this.roundData.getRoundScores();
		return roundScores.get(team);
	}

	private RoundData initializeRound(final Table table, final RuleSet ruleSet) {
		return new RoundData(table, ruleSet);
	}

	private void printRoundScores(final Map<Team, Score> roundScores) {
		this.logger.debug("--- Round Scores");
		for (Team team : roundScores.keySet()) {
			this.logger.debug(team + " scores: " + roundScores.get(team) + " points");
		}
	}

}
