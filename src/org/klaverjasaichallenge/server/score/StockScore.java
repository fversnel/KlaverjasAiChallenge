
package org.klaverjasaichallenge.server.score;

import org.klaverjasaichallenge.server.Trick;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.Card;

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
