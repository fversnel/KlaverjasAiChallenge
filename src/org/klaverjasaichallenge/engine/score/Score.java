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
		this.stockScore = new Points();
		this.roemScore = new Points();
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
		return Points.plus(getRoemScore(), getStockScore());
	}

	public boolean totalScorebiggerThan(final Score otherScore) {
		return Points.biggerThan(this.getTotalScore(),
				otherScore.getTotalScore());
	}

	public boolean totalScoreBiggerThanOrEquals(final Score otherScore) {
		return Points.biggerThanOrEquals(this.getTotalScore(),
				otherScore.getTotalScore());
	}
	
	public boolean totalScoreSmallerThan(final Score otherScore) {
		return Points.smallerThan(this.getTotalScore(),
				otherScore.getTotalScore());
	}
	
	public Score plus(Score score) {
		final Points resultStockScore = Points.plus(getStockScore(), score.getStockScore());
		final Points resultRoemScore = Points.plus(getRoemScore(), score.getRoemScore());
		return new Score(resultStockScore, resultRoemScore);
	}

	@Override
	public String toString() {
		return "Score [Total: " + this.getTotalScore() + 
				", Stock: " + this.stockScore + 
				", Roem: " + this.roemScore +
				"]";
	}

}
