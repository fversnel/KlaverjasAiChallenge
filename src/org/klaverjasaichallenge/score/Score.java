
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {
	private static final Points LAST_TRICK = new Points(10);

	public static Points calculateStockScore(final Trick trick, final Suit trump) {
		return StockScore.calculateScore(trick, trump);
	}

	public static Points calculateRoemScore(final Trick trick, final Suit trump) {
		return RoemScore.calculateScore(trick, trump);
	}

	public static Points calculateMarchScore(final List<Trick> tricks) {
		return MarchScore.calculateScore(tricks);
	}

	public static Points getLastTrickScore() {
		return LAST_TRICK;
	}

}
