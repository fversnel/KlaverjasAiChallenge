
package org.klaverjasaichallenge.engine.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
class StockScore {
	private final static Points LAST_TRICK_POINTS = new Points(10);

	public static Points calculateScore(final Trick trick) {
		Points totalPoints = Points.ZERO();
		final Suit trump = trick.getTrump();

		for(final Card card : trick.getCards()) {
			final Points cardPoints = card.getPoints(trump);
			totalPoints = totalPoints.plus(cardPoints);
		}

		if(trick.isLastTrick()) {
			totalPoints = totalPoints.plus(LAST_TRICK_POINTS);
		}

		return totalPoints;
	}

}
