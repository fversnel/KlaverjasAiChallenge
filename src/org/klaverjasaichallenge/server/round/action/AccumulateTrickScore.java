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
public class AccumulateTrickScore extends RoundAction {
	private final Table table;

	private final Logger logger;

	public AccumulateTrickScore(final RoundData roundData) {
		super(roundData);

		this.table = this.roundData.getTable();

		this.logger = Logger.getLogger(this.getClass());
	}

	@Override
	public RoundAction execute() {
		List<Team> teams = this.table.getTeams();
		for(final Team team : teams) {
			Score teamScore = this.accumlateTrickScore(team);
			this.roundData.addRoundScore(team, teamScore);
		}

		return new DetermineRoundScore(this.roundData);
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

}
