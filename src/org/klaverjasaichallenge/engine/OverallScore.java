package org.klaverjasaichallenge.engine;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;

import org.klaverjasaichallenge.engine.round.data.RoundResult;
import org.klaverjasaichallenge.engine.score.Score;

public class OverallScore {
	private final List<RoundResult> roundResults = new ArrayList<RoundResult>();

	public void add(final RoundResult roundResult) {
		this.roundResults.add(roundResult);
	}

	public Score getScore(final Team team) {
		Score totalScore = new Score();

		for(RoundResult roundResult : this.roundResults) {
			totalScore = totalScore.plus(roundResult.getScore(team));
		}

		return totalScore;
	}

	public int getNumberOfPlays(final Team team) {
		Iterable<RoundResult> roundsPlayed = Iterables.filter(roundResults,
				new Predicate<RoundResult>() {
					public boolean apply(RoundResult roundResult) {
						return roundResult.plays(team);
					}
				});

		return Iterables.size(roundsPlayed);
	}

	public int getNumberOfWets(final Team team) {
		Iterable<RoundResult> roundsWet = Iterables.filter(roundResults,
				new Predicate<RoundResult>() {
					public boolean apply(RoundResult roundResult) {
						return roundResult.isWet(team);
					}
				});

		return Iterables.size(roundsWet);
	}

	public int getNumberOfMarchings(final Team team) {
		Iterable<RoundResult> roundsMarching = Iterables.filter(roundResults,
				new Predicate<RoundResult>() {
					public boolean apply(RoundResult roundResult) {
						return roundResult.isMarching(team);
					}
				});

		return Iterables.size(roundsMarching);
	}

}
