
package org.klaverjasaichallenge.score;

import java.util.List;
import java.util.ArrayList;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.rank.*;

class RoemScore {
	private static final Points FOUR_JACKS = new Points(200);
	private static final Points FOUR_CARDS_SAME_RANK = new Points(100);
	private static final Points FOUR_CONSECUTIVE_CARDS = new Points(30);
	private static final Points THREE_CONSECUTIVE_CARDS = new Points(20);
	private static final Points STUK = new Points(20);

	private static final int FOUR_CARDS = 4;
	private static final int THREE_CARDS = 3;

	private static final Order CONSECUTIVE_DIFFERENCE = new Order(1);
	private static final Order FIRST_HIGH_CARD = new Order(4);
	private static final Order JACK = new Jack().getRoemOrder();

	public static Points calculateScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		score = Points.plus(score, calculateFourCardsSameRankScore(trick));
		score = Points.plus(score, calculateFourConsecutiveCardsScore(trick));
		score = Points.plus(score, calculateThreeConsecutiveCardsScore(trick));
		score = Points.plus(score, calculateStukScore(trick, trump));

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

	private static Points calculateConsecutiveCardsScore(
			final Trick trick, final int amountConsecutiveCards,
			final Points consecutiveScore) {
		Points score = new Points();

		List<Order> roemOrder = new ArrayList<Order>();
		List<Card> cards = trick.getCards();
		Suit cardSuit = cards.get(0).getSuit();
		for(Card card : cards) {
			if(cardSuit.equals(card.getSuit())) {
				roemOrder.add(card.getRoemOrder());
			}
		}

		if(roemOrder.size() >= amountConsecutiveCards) {
			int consecutiveCards = 1;

			Order.sort(roemOrder);

			for(int currentIndex = 0; currentIndex < roemOrder.size() - 1;
					currentIndex++) {
				int nextIndex = currentIndex + 1;

				Order currentOrder = roemOrder.get(currentIndex);
				Order nextOrder = roemOrder.get(nextIndex);
				Order toCompare = Order.minus(nextOrder, currentOrder);

				if(toCompare.equals(CONSECUTIVE_DIFFERENCE)) {
					consecutiveCards++;
				}
			}

			if(consecutiveCards >= amountConsecutiveCards) {
				score = consecutiveScore;
			}
		}

		return score;
	}

	private static Points calculateThreeConsecutiveCardsScore(final Trick trick) {
		return calculateConsecutiveCardsScore(trick, THREE_CARDS, THREE_CONSECUTIVE_CARDS);
	}

	private static Points calculateFourConsecutiveCardsScore(final Trick trick) {
		return calculateConsecutiveCardsScore(trick, FOUR_CARDS, FOUR_CONSECUTIVE_CARDS);
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
