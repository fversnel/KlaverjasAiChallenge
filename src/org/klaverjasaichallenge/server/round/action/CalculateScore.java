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
		List<Trick> tricks = this.roundData.getTricksPlayed();
		assert(tricks.size() == Trick.COUNT);

		Table table = this.roundData.getTable();
		Player trumpPlayer = this.roundData.getTrumpPlayer();

		final Team teamOffensive = table.getTeamFromPlayer(trumpPlayer);
		final Team teamDefensive = table.getOtherTeam(trumpPlayer);
		final Map<Team, Score> roundScores = this.initializeRoundScores(table.getTeams());

		for(int trickId = 0; trickId < Trick.COUNT; trickId++) {
			final Trick trick = tricks.get(trickId);
			final Player winner = trick.getWinner();
			final Team winningTeam = table.getTeamFromPlayer(winner);
			Score trickScore = trick.getScore();

			// For the last trick, award extra points
			if(trickId == LAST_TRICK_ID) {
				trickScore = new Score(Points.plus(trickScore.getStockScore(), Score.LAST_TRICK_POINTS), trickScore
						.getRoemScore(), false);
			}

			final Score previousScore = roundScores.get(winningTeam);
			roundScores.put(winningTeam, Score.plus(previousScore, trickScore));
		}

		// Going wet
		if(Score
				.totalScoreBiggerThanOrEquals(roundScores.get(teamDefensive), roundScores.get(teamOffensive))) {
			// Winners get the roem of both teams

			final Score defensiveScore = new Score(new Points(162), new Points(Points.plus(roundScores
					.get(teamDefensive).getRoemScore(), roundScores.get(teamOffensive).getRoemScore())), false);
			roundScores.put(teamDefensive, defensiveScore);

			// The team that goes gets 0 points
			roundScores.put(teamOffensive, new Score(new Points(), new Points(), true));

			this.logger.debug("--- " + teamOffensive + " goes wet! OMG");
		}

		// Marching
		if (roundScores.get(teamOffensive).getStockScore().equals(Score.MAXIMUM_SCORE)) {
			final Score newScore = new Score(roundScores.get(teamOffensive).getStockScore(), Points.plus(
					roundScores.get(teamOffensive).getRoemScore(), Score.MARCH_POINTS), false);
			roundScores.put(teamOffensive, newScore);
			this.logger.debug("--- " + teamOffensive + " goes marching");

		}

		this.roundData.addRoundScores(roundScores);

		return null;
	}

	private Map<Team, Score> initializeRoundScores(final List<Team> teams) {
		final Map<Team, Score> roundScores = new HashMap<Team, Score>();
		for (final Team team : teams) {
			roundScores.put(team, new Score());
		}

		return roundScores;
	}

}
