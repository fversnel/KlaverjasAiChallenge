
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {

	public static Points getScore(List<Trick> tricks, Suit trump) {
		Points totalPoints = new Points();

		totalPoints = Points.plus(totalPoints, StockScore.calculateScore(tricks, trump));
		totalPoints = Points.plus(totalPoints, SpecialScore.calculateScore(tricks, trump));

		return totalPoints;
	}

}
