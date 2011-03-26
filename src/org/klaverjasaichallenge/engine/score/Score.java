package org.klaverjasaichallenge.engine.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;

/**
 *
 * @author Frank Versnel
 */
public class Score {
	public static final Points MAXIMUM_STOCK_SCORE = new Points(162);
	public static final Points MARCH_POINTS = new Points(100);

	private static final int FULL_TRICK = 4;

	private Points stockScore;
	private Points roemScore;
	
	private int wetCount = 0;
	private int marchCount = 0;
	private int playCount = 0;

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
	
	public void incrementWetCount() {
		this.wetCount++;
	}
	
	public int getWetCount() {
		return this.wetCount;
	}
	
	public void incrementPlayCount() {
		this.playCount++;
	}

	public int getPlayCount() {
		return this.playCount;
	}
	
	public void incrementMarchCount() {
		this.marchCount++;
	}
	
	public int getMarchCount() {
		return this.marchCount;
	}

	public void plus(final Score otherScore) {
		this.stockScore = Points.plus(this.stockScore, otherScore.getStockScore());
		this.roemScore = Points.plus(this.roemScore, otherScore.getRoemScore());

		this.playCount = this.playCount + otherScore.getPlayCount();
		this.marchCount = this.marchCount + otherScore.getMarchCount();
		this.wetCount = this.wetCount + otherScore.getWetCount();
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

	@Override
	public String toString() {
		return "Score [Total: " + this.getTotalScore() + 
				", Stock: " + this.stockScore + 
				", Roem: " + this.roemScore +
				", Play count: " + this.playCount +
				", Wet count: " + this.wetCount +
				", March count: " + this.marchCount +
				"]";
	}

}
