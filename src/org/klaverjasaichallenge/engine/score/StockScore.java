
package org.klaverjasaichallenge.engine.score;

import org.klaverjasaichallenge.engine.round.Trick;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
class StockScore {

	public static Points calculateScore(final Trick trick) {
		Points totalPoints = new Points();
		Suit trump = trick.getTrump();

		for(final Card card : trick.getCards()) {
			final Points cardPoints = card.getPoints(trump);
			totalPoints = Points.plus(totalPoints, cardPoints);
		}

		return totalPoints;
	}
}
