
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.Card;

class StockScore {

	public static Points calculateScore(List<Trick> tricks, Suit trump) {
		Points totalPoints = new Points();

		for(Trick trick : tricks) {
			for(Card card : trick.getCards()) {
				Points cardPoints = card.getPoints(trump);
				totalPoints = Points.plus(totalPoints, cardPoints);
			}
		}

		return totalPoints;
	}
}
