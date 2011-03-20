package org.klaverjasaichallenge.engine.round.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
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
	private static final RoundAction NO_NEXT_ACTION = null;

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
			teamScore = this.determineWetScore(team, teamScore);
			teamScore = this.determineMarchScore(team, teamScore);
			this.roundData.addRoundScore(team, teamScore);
		}

		return NO_NEXT_ACTION;
	}

	private Score determineWetScore(final Team team, final Score teamScore) {
		Score newTeamScore = teamScore;

		Team otherTeam = this.table.getOtherTeam(team);
		Score otherTeamScore = this.roundData.getRoundScore(otherTeam);

		// If we get wet:
		if(team.hasPlayer(this.trumpPlayer) && Score.isWet(teamScore, otherTeamScore)) {
			newTeamScore = new Score();

			this.logger.debug("--- " + team + " goes wet! OMG!!!");
		}
		// If the other team gets wet:
		else if(otherTeam.hasPlayer(this.trumpPlayer) && Score.isWet(otherTeamScore, teamScore)) {
			// The winning team gets the roem of both teams as well as the
			// maximum stock score.
			newTeamScore = new Score(Score.MAXIMUM_STOCK_SCORE,
					Points.plus(teamScore.getRoemScore(),
							otherTeamScore.getRoemScore()));
		}

		return newTeamScore;
	}

	private Score determineMarchScore(final Team team, final Score teamScore) {
		Score newTeamScore = teamScore;

		if(team.hasPlayer(this.trumpPlayer) && Score.isMarching(teamScore)) {
			newTeamScore = new Score(teamScore.getStockScore(), Points.plus(
						teamScore.getRoemScore(), Score.MARCH_POINTS));

			this.logger.debug("--- " + team + " goes marching.");
		}

		return newTeamScore;
	}

}
