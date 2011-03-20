
package org.klaverjasaichallenge.engine.score;

import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
class StockScore {
	private final static Points LAST_TRICK_POINTS = new Points(10);

	public static Points calculateScore(final EngineTrick trick) {
		Points totalPoints = new Points();
		Suit trump = trick.getTrump();

		for(final Card card : trick.getCards()) {
			final Points cardPoints = card.getPoints(trump);
			totalPoints = Points.plus(totalPoints, cardPoints);
		}

		if(trick.isLastTrick()) {
			totalPoints = Points.plus(totalPoints, LAST_TRICK_POINTS);
		}

		return totalPoints;
	}
}
