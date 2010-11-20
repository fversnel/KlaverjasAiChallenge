
package org.klaverjasaichallenge.score;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.Card;

class StockScore {

	public static Points calculateScore(final Trick trick, final Suit trump) {
		Points totalPoints = new Points();

		for(final Card card : trick.getCards()) {
			final Points cardPoints = card.getPoints(trump);
			totalPoints = Points.plus(totalPoints, cardPoints);
		}

		return totalPoints;
	}
}
