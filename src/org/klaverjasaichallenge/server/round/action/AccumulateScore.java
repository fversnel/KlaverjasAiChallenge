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

/**
 * Accumulates the score players have amassed this Round.
 */
public class AccumulateScore extends RoundAction {
	private static final RoundAction NO_NEXT_ACTION = null;

	private final Table table;
	private final Player trumpPlayer;

	private final Logger logger;

	public AccumulateScore(final RoundData roundData) {
		super(roundData);

		this.table = this.roundData.getTable();
		this.trumpPlayer = this.roundData.getTrumpPlayer();

		this.logger = Logger.getLogger(AccumulateScore.class);
	}

	@Override
	public RoundAction execute() {
		List<Team> teams = this.table.getTeams();
		this.accumlateTrickScores(teams);
		this.determineRoundScores(teams);

		return NO_NEXT_ACTION;
	}

	private void accumlateTrickScores(final List<Team> teams) {
		for(final Team team : teams) {
			Score teamScore = this.accumlateTrickScore(team);
			this.roundData.addRoundScore(team, teamScore);
		}
	}

	private void determineRoundScores(final List<Team> teams) {
		for(final Team team : teams) {
			Score teamScore = this.roundData.getRoundScore(team);
			teamScore = this.accumlateWetScore(team, teamScore);
			teamScore = this.accumlateMarchScore(team, teamScore);
			this.roundData.addRoundScore(team, teamScore);
		}
	}

	private Score accumlateTrickScore(final Team team) {
		Score roundScore = new Score();
		List<Trick> playedTricks = this.roundData.getTricksPlayed();
		for(Trick playedTrick : playedTricks) {
			final Player winningPlayer = playedTrick.getWinner();
			final Team winningTeam = table.getTeamFromPlayer(winningPlayer);

			if(team.equals(winningTeam)) {
				final Score trickScore = playedTrick.getScore();
				roundScore = Score.plus(roundScore, trickScore);
			}
		}

		return roundScore;
	}

	private Score accumlateWetScore(final Team team, final Score teamScore) {
		Score newTeamScore = teamScore;

		Team otherTeam = table.getOtherTeam(team);
		Score otherTeamScore = this.roundData.getRoundScore(otherTeam);
		// If the other team gets wet:
		if(otherTeam.hasPlayer(this.trumpPlayer) && Score.isWet(otherTeamScore, teamScore)) {
			// The winning team gets the roem of both teams as well as the
			// maximum stock score.
			newTeamScore = new Score(Score.MAXIMUM_STOCK_SCORE,
					Points.plus(teamScore.getRoemScore(),
							otherTeamScore.getRoemScore()));
		}
		// If we get wet:
		else if(team.hasPlayer(this.trumpPlayer) && Score.isWet(teamScore, otherTeamScore)) {
			newTeamScore = new Score();

			this.logger.debug("--- " + team + " goes wet! OMG!!!");
		}

		return newTeamScore;
	}

	private Score accumlateMarchScore(final Team team, final Score teamScore) {
		Score newTeamScore = teamScore;

		if(team.hasPlayer(this.trumpPlayer) && Score.isMarching(teamScore)) {
			newTeamScore = new Score(teamScore.getStockScore(), Points.plus(
						teamScore.getRoemScore(), Score.MARCH_POINTS));

			this.logger.debug("--- " + team + " goes marching.");
		}

		return newTeamScore;
	}

}
