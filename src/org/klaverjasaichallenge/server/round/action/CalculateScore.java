package org.klaverjasaichallenge.server.round.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.Team;
import org.klaverjasaichallenge.server.round.Trick;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

class CalculateScore extends RoundAction {
	private static final int LAST_TRICK_ID = Trick.COUNT - 1;

	private final Logger logger;

	public CalculateScore(final RoundData roundData) {
		super(roundData);

		this.logger = Logger.getLogger(CalculateScore.class);
	}

	/**
	 * Calculates the points players have amassed this Round
	 *
	 */
	@Override
	public RoundAction execute() {
		List<Trick> playedTricks = this.roundData.getTricksPlayed();
		assert(playedTricks.size() == Trick.COUNT);

		Table table = this.roundData.getTable();
		Player trumpPlayer = this.roundData.getTrumpPlayer();

		final Team teamOffensive = table.getTeamFromPlayer(trumpPlayer);
		final Team teamDefensive = table.getOtherTeam(trumpPlayer);

		for(Trick playedTrick : playedTricks) {
			final Player winner = playedTrick.getWinner();
			final Team winningTeam = table.getTeamFromPlayer(winner);
			Score trickScore = playedTrick.getScore();

			final Score previousTrickScore = this.roundData.getRoundScore(winningTeam);
			this.roundData.addRoundScore(winningTeam, Score.plus(previousTrickScore, trickScore));
		}

		Score teamOffensiveScore = this.roundData.getRoundScore(teamOffensive);
		Score teamDefensiveScore = this.roundData.getRoundScore(teamDefensive);
		// Going wet
		if(Score.totalScoreBiggerThanOrEquals(teamDefensiveScore, teamOffensiveScore)) {
			// The winning team get the roem of both teams as well as the
			// maximum stock score.
			teamDefensiveScore = new Score(Score.MAXIMUM_STOCK_SCORE,
					new Points(Points.plus(teamDefensiveScore.getRoemScore(),
							teamOffensiveScore.getRoemScore())));
			this.roundData.addRoundScore(teamDefensive, teamDefensiveScore);

			// The team that played the trump get no points at all.
			teamOffensiveScore = new Score(new Points(), new Points());
			// What they do get, is an extra wet count :).
			teamOffensiveScore.incrementWets();
			// Finally, add the score the RoundData object.
			this.roundData.addRoundScore(teamOffensive, teamOffensiveScore);

			this.logger.debug("--- " + teamOffensive + " goes wet! OMG!!!");
		}

		// Marching
		else if (teamOffensiveScore.getStockScore().equals(Score.MAXIMUM_STOCK_SCORE)) {
			teamOffensiveScore = new Score(teamOffensiveScore.getStockScore(), Points.plus(
					teamOffensiveScore.getRoemScore(), Score.MARCH_POINTS));
			this.roundData.addRoundScore(teamOffensive, teamOffensiveScore);

			this.logger.debug("--- " + teamOffensive + " goes marching.");
		}

		return null;
	}

}
