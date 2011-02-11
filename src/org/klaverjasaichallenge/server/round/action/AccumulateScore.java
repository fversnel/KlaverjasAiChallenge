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

class AccumulateScore extends RoundAction {
	private static final RoundAction NO_NEXT_ACTION = null;

	private final Team teamOffensive;
	private Score teamOffensiveScore;

	private final Team teamDefensive;
	private Score teamDefensiveScore;

	private final Logger logger;

	public AccumulateScore(final RoundData roundData) {
		super(roundData);

		final Player trumpPlayer = this.roundData.getTrumpPlayer();
		final Table table = this.roundData.getTable();

		this.teamOffensive = table.getTeamFromPlayer(trumpPlayer);
		this.teamOffensiveScore = this.roundData.getRoundScore(teamOffensive);

		this.teamDefensive = table.getOtherTeam(trumpPlayer);
		this.teamDefensiveScore = this.roundData.getRoundScore(teamDefensive);

		this.logger = Logger.getLogger(CalculateScore.class);
	}

	/**
	 * Accumulates the points players have amassed this Round.
	 */
	@Override
	public RoundAction execute() {
		this.accumlateTrickScores();
		if(Score.isWet(teamOffensiveScore, teamDefensiveScore)) {
			this.accumlateWetScores();
		} else if(Score.isMarching(teamOffensiveScore, teamDefensiveScore)) {
			this.accumlateMarchScore();
		}

		return NO_NEXT_ACTION;
	}

	private void accumlateTrickScores() {
		Table table = this.roundData.getTable();

		List<Trick> playedTricks = this.roundData.getTricksPlayed();
		for(Trick playedTrick : playedTricks) {
			final Player winner = playedTrick.getWinner();
			final Team winningTeam = table.getTeamFromPlayer(winner);

			final Score trickScore = playedTrick.getScore();
			final Score previousTrickScores = this.roundData.getRoundScore(winningTeam);
			final Score newScore = Score.plus(previousTrickScores, trickScore);

			this.roundData.addRoundScore(winningTeam, newScore);
		}
	}

	private void accumlateWetScores() {
		// The winning team get the roem of both teams as well as the
		// maximum stock score.
		teamDefensiveScore = new Score(Score.MAXIMUM_STOCK_SCORE,
				new Points(Points.plus(teamDefensiveScore.getRoemScore(),
						teamOffensiveScore.getRoemScore())));
		this.roundData.addRoundScore(teamDefensive, teamDefensiveScore);

		// The team that played the trump get no points at all.
		teamOffensiveScore = new Score(new Points(), new Points());
		// What they do get though, is an extra wet count :).
		teamOffensiveScore.incrementWets();
		// Finally, add the score the RoundData object.
		this.roundData.addRoundScore(teamOffensive, teamOffensiveScore);

		this.logger.debug("--- " + teamOffensive + " goes wet! OMG!!!");
	}

	private void accumlateMarchScore() {
		teamOffensiveScore = new Score(teamOffensiveScore.getStockScore(), Points.plus(
					teamOffensiveScore.getRoemScore(), Score.MARCH_POINTS));
		this.roundData.addRoundScore(teamOffensive, teamOffensiveScore);

		this.logger.debug("--- " + teamOffensive + " goes marching.");
	}

}
