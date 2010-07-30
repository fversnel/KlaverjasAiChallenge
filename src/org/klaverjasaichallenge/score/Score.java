package org.klaverjasaichallenge.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {

	public Score() {
		
	}

	public static Points calculateStockScore(Trick trick, Suit trump) {
		return StockScore.calculateScore(trick, trump);
	}

	public static Points calculateRoemScore(Trick trick, Suit trump) {
		return RoemScore.calculateScore(trick, trump);
	}
}
