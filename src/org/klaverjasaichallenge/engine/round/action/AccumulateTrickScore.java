package org.klaverjasaichallenge.engine.round.action;

import java.util.List;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;

/**
 * Accumulates the score players have amassed this Round.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class AccumulateTrickScore extends RoundAction {
	private final Table table;

	public AccumulateTrickScore(final RoundData roundData) {
		super(roundData);

		this.table = this.roundData.getTable();
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
		List<EngineTrick> playedTricks = this.roundData.getTricksPlayed();
		for(EngineTrick playedTrick : playedTricks) {
			final Player winningPlayer = playedTrick.getWinner();
			final Team winningTeam = this.table.getTeamFromPlayer(winningPlayer);

			if(team.equals(winningTeam)) {
				final Score trickScore = new Score(playedTrick);
				roundScore = Score.plus(roundScore, trickScore);
			}
		}

		return roundScore;
	}

}
