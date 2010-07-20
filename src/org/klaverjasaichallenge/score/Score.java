
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {

	public static Points calculateStockScore(List<Trick> tricks, Suit trump) {
		return StockScore.calculateScore(tricks, trump);
	}

	public static Points calculateRoemScore(final List<Trick> tricks, final Suit trump) {
		return SpecialScore.calculateScore(tricks, trump);
	}

	public static Points calculateMarchScore(final List<Trick> tricks) {
		return MarchScore.calculateScore(tricks);
	}

}
