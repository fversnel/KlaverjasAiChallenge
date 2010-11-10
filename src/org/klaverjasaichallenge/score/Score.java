package org.klaverjasaichallenge.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {
	private final Points stockScore;
	private final Points roemScore;

	public final static Points MARCH_POINTS = new Points(100);
	public final static Points LAST_TRICK_POINTS = new Points(10);

	public Score() {
		this.stockScore = new Points();
		this.roemScore = new Points();
	}

	public Score(Points stockScore, Points roemScore) {
		this.stockScore = stockScore;
		this.roemScore = roemScore;
	}

	public static Points calculateStockScore(Trick trick, Suit trump) {
		return StockScore.calculateScore(trick, trump);
	}

	public static Points calculateRoemScore(Trick trick, Suit trump) {
		return RoemScore.calculateScore(trick, trump);
	}

	public Points getRoemScore() {
		return roemScore;
	}

	public Points getStockScore() {
		return stockScore;
	}

	public Points getTotalScore() {
		return Points.plus(getRoemScore(), getStockScore());
	}


	public static Score plus(final Score leftScore, final Score rightScore) {
		return new Score(Points.plus(leftScore.getStockScore(),rightScore.getStockScore()), Points.plus(leftScore.getRoemScore(),rightScore.getRoemScore()));
	}

	public static boolean totalScorebiggerThan(final Score leftHandSide, final Score rightHandSide) {
		return Points.biggerThan(leftHandSide.getTotalScore(), rightHandSide.getTotalScore());
	}

	public String toString() {
		return "Score [Total: "+this.getTotalScore()+", Stock: "+ this.getStockScore() +", Roem: " + this.getRoemScore() + "]";
	}
}
