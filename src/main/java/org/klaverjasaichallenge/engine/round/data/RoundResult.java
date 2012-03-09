package org.klaverjasaichallenge.engine.round.data;

import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.Team;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;

/**
 *
 * @author Frank Versnel
 */
public class RoundResult {
	private static final Points MAXIMUM_STOCK_SCORE = new Points(162);
	private static final Points MARCH_POINTS = new Points(100);

	private final Table table;
	private final TrumpPlayer trumpPlayer;
	private final PlayedTricks playedTricks;

	public RoundResult(final Table table, final TrumpPlayer trumpPlayer, final PlayedTricks playedTricks) {
		this.table = table;
		this.trumpPlayer = trumpPlayer;
		this.playedTricks = playedTricks;
	}

	public boolean plays(final Team team) {
		final Team playingTeam = this.table.getTeamFromPlayer(this.trumpPlayer.getPlayer());
		return team.equals(playingTeam);
	}

	public boolean isWet(final Team team) {
		final Score trickScoreTeam = accumulateTrickScore(team);
		final Score trickScoreOtherTeam = accumulateTrickScore(this.table.getOtherTeam(team));

		return trickScoreTeam.totalScoreSmallerThan(trickScoreOtherTeam) && plays(team);
	}

	public boolean isMarching(final Team team) {
		final Score trickScoreTeam = accumulateTrickScore(team);

		return trickScoreTeam.getStockScore().equals(MAXIMUM_STOCK_SCORE) && plays(team);
	}

	public Score getScore(final Team team) {
		Score teamScore = accumulateTrickScore(team);
		teamScore = determineWetScore(team, teamScore);
		teamScore = determineMarchScore(team, teamScore);

		return teamScore;
	}

	private Score accumulateTrickScore(final Team team) {
		Iterable<Trick> tricksWon = Iterables.filter(this.playedTricks.getTricksPlayed(), new Predicate<Trick>() {
			public boolean apply(final Trick trick) {
				final Player trickWinner = playedTricks.getWinner(trick);

				return team.hasPlayer(trickWinner);
			}
		});

		Score teamScore = new Score();
		for(final Trick trick : tricksWon) {
			teamScore = teamScore.plus(new Score(trick));
		}

		return teamScore;
	}

	private Score determineWetScore(final Team team, final Score trickScoreTeam) {
		final Team otherTeam = this.table.getOtherTeam(team);

		Score newTeamScore = trickScoreTeam;

		if(isWet(team)) {
			newTeamScore = new Score();
		} else if(isWet(otherTeam)) {
			// The winning team gets the roem of both teams as well as the
			// maximum stock score.
			final Score trickScoreOtherTeam = accumulateTrickScore(otherTeam);

			newTeamScore = new Score(MAXIMUM_STOCK_SCORE, trickScoreTeam.getRoemScore().plus(
						trickScoreOtherTeam.getRoemScore()));
		}

		return newTeamScore;
	}

	private Score determineMarchScore(final Team team, final Score trickScoreTeam) {
		Score newTeamScore = trickScoreTeam;

		if(isMarching(team)) {
			newTeamScore = new Score(trickScoreTeam.getStockScore(), trickScoreTeam.getRoemScore().plus(MARCH_POINTS));
		}

		return newTeamScore;
	}


}
