package org.klaverjasaichallenge.engine.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;

/**
 *
 * @author Frank Versnel
 */
public class Score {
	private static final int FULL_TRICK = 4;

	private final Points stockScore;
	private final Points roemScore;

	public Score() {
		this.stockScore = Points.ZERO();
		this.roemScore = Points.ZERO();
	}

	public Score(final Points stockScore, final Points roemScore) {
		this.stockScore = stockScore;
		this.roemScore = roemScore;
	}

	public Score(final Trick trick) {
		assert(trick.numberOfCards() == FULL_TRICK) : "Score cannot be calculated for " +
				"a trick that is not finished. Trick contains " + trick.numberOfCards() +
				", but should contain: " + FULL_TRICK + " cards.";

		this.stockScore = StockScore.calculateScore(trick);
		this.roemScore = RoemScore.calculateScore(trick);
	}

	public Points getRoemScore() {
		return this.roemScore;
	}

	public Points getStockScore() {
		return this.stockScore;
	}

	public Points getTotalScore() {
		return getRoemScore().plus(getStockScore());
	}

	public boolean totalScorebiggerThan(final Score otherScore) {
		return this.getTotalScore().biggerThan(otherScore.getTotalScore());
	}

	public boolean totalScoreBiggerThanOrEquals(final Score otherScore) {
		return this.getTotalScore().biggerThanOrEquals(otherScore.getTotalScore());
	}

	public boolean totalScoreSmallerThan(final Score otherScore) {
		return this.getTotalScore().smallerThan(otherScore.getTotalScore());
	}

	public Score plus(Score score) {
		final Points resultStockScore = getStockScore().plus(score.getStockScore());
		final Points resultRoemScore = getRoemScore().plus(score.getRoemScore());
		return new Score(resultStockScore, resultRoemScore);
	}

	@Override
	public String toString() {
		return String.format("Score [Total: %s, Stock: %s, Roem: %s",
				this.getTotalScore(), this.stockScore, this.roemScore);
	}

}
