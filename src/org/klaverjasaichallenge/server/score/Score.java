package org.klaverjasaichallenge.server.score;

import org.klaverjasaichallenge.server.round.Trick;
import org.klaverjasaichallenge.shared.Points;

public class Score {
	private static final int FULL_TRICK = 4;

	private final Points stockScore;
	private final Points roemScore;

	private int wets = 0;

	public final static Points MARCH_POINTS = new Points(100);
	public final static Points LAST_TRICK_POINTS = new Points(10);

	public Score() {
		this.stockScore = new Points();
		this.roemScore = new Points();
	}

	public Score(final Points stockScore, final Points roemScore) {
		this.stockScore = stockScore;
		this.roemScore = roemScore;
	}

	public Score(final Points stockScore, final Points roemScore, final boolean wet) {
		this.stockScore = stockScore;
		this.roemScore = roemScore;

		if(wet) {
			this.wets++;
		}
	}

	public Score(final Trick trick) {
		assert(trick.numberOfCards() == FULL_TRICK) : "Score cannot be calculated for " +
				"a trick that is not finished. Trick contains " + trick.numberOfCards() +
				", but should contain: " + FULL_TRICK + " cards.";

		this.stockScore = StockScore.calculateScore(trick);
		this.roemScore = RoemScore.calculateScore(trick);
	}

	private Score(final Points stockScore, final Points roemScore, final int wets) {
		this.stockScore = stockScore;
		this.roemScore = roemScore;
		this.wets = wets;
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

	public int getWets() {
		return this.wets;
	}

	public static Score plus(final Score leftScore, final Score rightScore) {
		return new
			Score(Points.plus(leftScore.getStockScore(),rightScore.getStockScore()),
					Points.plus(leftScore.getRoemScore(),rightScore.getRoemScore()),
					leftScore.getWets() + rightScore.getWets());
	}

	public static boolean totalScorebiggerThan(final Score leftHandSide, final
			Score rightHandSide) {
		return Points.biggerThan(leftHandSide.getTotalScore(),
				rightHandSide.getTotalScore());
	}

	public static boolean totalScoreBiggerThanOrEquals(final Score
			leftHandSide, final Score rightHandSide) {
		return Points.biggerThanOrEquals(leftHandSide.getTotalScore(),
				rightHandSide.getTotalScore());
	}

	@Override
	public String toString() {
		return "Score [Total: "+this.getTotalScore()+", Stock: " +
			this.getStockScore() +", Roem: " + this.getRoemScore() +
			", Wets: " + this.getWets() + "]";
	}

}
