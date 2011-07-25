package org.klaverjasaichallenge.engine;

import org.klaverjasaichallenge.engine.round.Round;
import org.klaverjasaichallenge.engine.round.data.RoundResult;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlaverjasGame {
	private final static Logger logger = LoggerFactory.getLogger(KlaverjasGame.class);

	private RuleSet ruleSet;
	
	private final Team team1;
	private final Team team2;

	private int numberOfRounds;
	
	public KlaverjasGame(final RuleSet ruleSet, final Team team1, final Team team2, final int numberOfRounds) {
		this.ruleSet = ruleSet;
		
		this.team1 = team1;
		this.team2 = team2;
		
		this.numberOfRounds = numberOfRounds;
	}
	
	public OverallScore play() {
		// Create a table object to coordinate the round starters
		Table table = new Table(this.team1, this.team2);
		OverallScore overallScore = new OverallScore();

		for(int currentRoundId = 1; currentRoundId <= this.numberOfRounds; currentRoundId++) {
			logger.debug("- Starting round: " + currentRoundId + " with " +
					"Players: " + table);

			// Play the round
			Round round = new Round(table, this.ruleSet);
			RoundResult roundResult = round.play();
			overallScore.add(roundResult);

			// Change the order of the players
			table.nextRound();

			logger.debug("\nRound scores:\n" +
						this.team1 + " scored " + roundResult.getScore(this.team1) + " points.\n" +
						this.team2 + " scored " + roundResult.getScore(this.team2) + " points.");
		}
		
		return overallScore;
	}

}
