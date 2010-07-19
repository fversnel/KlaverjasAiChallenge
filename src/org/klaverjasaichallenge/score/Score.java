
package org.klaverjasaichallenge.score;

import java.util.List;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Score {

	/**
	 * TODO Default returns 0, has to be changed.
	 */
	public static Points getScore(List<Card> cards) {
		return new Points();
	}

	/**
	 * TODO Translate 'roem' to English.
	 * TODO Default returns 0, has to be changed.
	 */
	private static Points getRoem() {
		return new Points();
	}

	private static Points calculateStockPoints(List<Card> cards, Suit trump) {
		Points totalPoints = new Points();

		for(Card card : cards) {
			Points cardPoints = card.getPoints(trump);
			totalPoints = Points.plus(totalPoints, cardPoints);
		}

		return totalPoints;
	}
}
