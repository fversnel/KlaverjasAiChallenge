package org.klaverjasaichallenge.engine.round.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.round.Round;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * Determines whether the teams have gone wet or gone marching and gives each
 * team the appropriate score.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class DetermineRoundScore extends RoundAction {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final Table table;
	private final Player trumpPlayer;

	public DetermineRoundScore(final RoundData roundData) {
		super(roundData);

		this.table = this.roundData.getTable();
		this.trumpPlayer = this.roundData.getTrumpPlayer();
	}

	@Override
	public RoundAction execute() {
		List<Team> teams = this.table.getTeams();
		for(final Team team : teams) {
			Score teamScore = this.roundData.getRoundScore(team);
			
			boolean teamPlays = team.hasPlayer(this.trumpPlayer);
			if(teamPlays) {
				teamScore.incrementPlayCount();
			}
			
			teamScore = this.determineWetScore(team, teamScore, teamPlays);
			teamScore = this.determineMarchScore(team, teamScore, teamPlays);
			
			this.roundData.addRoundScore(team, teamScore);
		}

		return Round.NO_NEXT_ACTION;
	}

	private Score determineWetScore(final Team team, final Score teamScore, final boolean teamPlays) {
		Score newTeamScore = teamScore;
		
		final Score otherTeamScore = this.roundData.getRoundScore(this.table.getOtherTeam(team));
		final boolean teamIsWet = teamScore.totalScoreSmallerThan(otherTeamScore);
		// If we get wet:
		if(teamPlays && teamIsWet) {
			newTeamScore = new Score();
			newTeamScore.incrementWetCount();
			
			this.logger.debug("--- " + team + " goes wet! OMG!!!");
		}
		
		final boolean otherTeamPlays = !teamPlays;
		final boolean otherTeamIsWet = !teamIsWet;
		// If the other team gets wet:
		if(otherTeamPlays && otherTeamIsWet) {
			// The winning team gets the roem of both teams as well as the
			// maximum stock score.
			newTeamScore = new Score(Score.MAXIMUM_STOCK_SCORE,
					Points.plus(teamScore.getRoemScore(),
							otherTeamScore.getRoemScore()));
		}

		return newTeamScore;
	}

	private Score determineMarchScore(final Team team, final Score teamScore, final boolean teamPlays) {
		Score newTeamScore = teamScore;
		
		final boolean isMarching = teamScore.getStockScore().equals(Score.MAXIMUM_STOCK_SCORE);
		if(teamPlays && isMarching) {
			newTeamScore = new Score(teamScore.getStockScore(), Points.plus(
						teamScore.getRoemScore(), Score.MARCH_POINTS));
			newTeamScore.incrementMarchCount();

			this.logger.debug("--- " + team + " goes marching.");
		}

		return newTeamScore;
	}

}
