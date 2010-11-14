
package org.klaverjasaichallenge.score;

import java.util.List;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.rank.*;

class RoemScore {
	private static final Points FOUR_JACKS = new Points(200);
	private static final Points FOUR_CARDS_SAME_RANK = new Points(100);
	private static final Points FOUR_CONSECUTIVE_CARDS = new Points(50);
	private static final Points THREE_CONSECUTIVE_CARDS = new Points(20);
	private static final Points STUK = new Points(20);

	private static final Order FIRST_HIGH_CARD = new Order(4);
	private static final Order JACK = new Jack().getRoemOrder();

	public static Points calculateScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		if(!trick.getCards().isEmpty()) {
			score = Points.plus(score, calculateFourCardsSameRankScore(trick));
			score = Points.plus(score, calculateConsecutiveCardsScore(trick));
			score = Points.plus(score, calculateStukScore(trick, trump));
		}

		return score;
	}

	private static Points calculateFourCardsSameRankScore(final Trick trick) {
		Points score = new Points();

		List<Card> cards = trick.getCards();
		Card firstCard = cards.get(0);
		Rank cardRank = firstCard.getRank();
		Order cardOrder = firstCard.getRoemOrder();
		if(cardOrder.isHigherOrSameAs(FIRST_HIGH_CARD)) {
			boolean test = true;
			for(Card card : cards) {
				Rank currentCardRank = card.getRank();
				if(!(currentCardRank.equals(cardRank))) {
					test = false;
				}
			}

			if(test) {
				// TODO This can be refactored by using polymorphism.
				// Perhaps some points can be assigned to a specific rank or
				// card.
				if(cardOrder.equals(JACK)) {
					score = FOUR_JACKS;
				} else {
					score = FOUR_CARDS_SAME_RANK;
				}
			}
		}

		return score;
	}

	/**
	 * TODO Check for each card if it is one, two, or three ranks higher
	 */
	private static Points calculateConsecutiveCardsScore(final Trick trick) {
		Points score = new Points();

		for(Card card : trick.getCards()) {
			boolean oneRankDifference = false;
			boolean twoRanksDifference = false;
			boolean threeRanksDifference = false;

			for(Card otherCard : trick.getCards()) {
				Order orderDifference = Order.minus(card.getRoemOrder(),
						otherCard.getRoemOrder());
				if(card.getSuit().equals(otherCard.getSuit())) {
					if(orderDifference.equals(new Order(1))) {
						oneRankDifference = true;
					}
					else if(orderDifference.equals(new Order(2))) {
						twoRanksDifference = true;
					}
					else if(orderDifference.equals(new Order(3))) {
						threeRanksDifference = true;
					}
				}
			}

			if(oneRankDifference && twoRanksDifference && threeRanksDifference) {
				score = FOUR_CONSECUTIVE_CARDS;
			}
			else if((oneRankDifference && twoRanksDifference) &&
					Points.biggerThan(THREE_CONSECUTIVE_CARDS, score)) {
				score = THREE_CONSECUTIVE_CARDS;
			}
		}

		return score;
	}

	private static Points calculateStukScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		boolean queen = false;
		boolean king = false;
		for(Card trickCard : trick.getCards()) {
			Suit cardSuit = trickCard.getSuit();
			Rank cardRank = trickCard.getRank();
			if(cardSuit.equals(trump)) {
				if(cardRank instanceof Queen) {
					queen = true;
				} else if(cardRank instanceof King) {
					king = true;
				}
			}
		}

		if(king && queen) {
			score = STUK;
		}

		return score;
	}

}
