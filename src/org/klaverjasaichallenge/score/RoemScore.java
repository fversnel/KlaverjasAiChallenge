
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

	public static Points calculateScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		score = Points.plus(score, calculateFourCardsSameRankScore(trick));
		score = Points.plus(score, calculateFourConsecutiveCardsScore(trick, trump));
		score = Points.plus(score, calculateThreeConsecutiveCardsScore(trick));
		score = Points.plus(score, calculateStukScore(trick, trump));

		return score;
	}

	private static Points calculateFourCardsSameRankScore(final Trick trick) {
		Points score = new Points();

		List<Card> cards = trick.getCards();
		Rank firstRank = cards.get(0).getRank();
		if(firstRank instanceof Ace
				|| firstRank instanceof King
				|| firstRank instanceof Queen
				|| firstRank instanceof Jack
				|| firstRank instanceof Ten) {
			boolean test = true;
			for(Card card : cards) {
				Class cardRank = card.getRank().getClass();	
				if(!(cardRank.isInstance(firstRank))) {
					test = false;
				}
			}

			if(test) {
				// TODO This can be refactored by using polymorphism.
				// Perhaps some points can be assigned to a specific rank or
				// card.
				if(firstRank instanceof Jack) {
					score = FOUR_JACKS;
				} else {
					score = FOUR_CARDS_SAME_RANK;
				}
			}
		}

		return score;
	}

	/**
	 * TODO Implement this method.
	 */
	private static Points calculateFourConsecutiveCardsScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		List<Order> roemOrder = new ArrayList<Order>();
		for(Card card : trick.getCards()) {
			Suit cardSuit = card.getSuit();
			if(cardSuit.getClass().isInstance(trump)) {
				roemOrder.add(card.getRank().getRoemOrder());
			}
		}

		if(roemOrder.size() >= FOUR_CARDS) {
			boolean cardConsecutive = true;

			roemOrder = Order.sort(roemOrder);
			for(int i = 0; i < 3; i++) {
				Order currentOrder = roemOrder.get(i);
				Order nextOrder = roemOrder.get(i + 1);
				Order toCompare = Order.minus(nextOrder, new Order(1));

				if(!currentOrder.equals(toCompare)) {
					cardConsecutive = false;
				}
			}

			if(cardConsecutive) {
				score = FOUR_CONSECUTIVE_CARDS;
			}
		}

		return score;
	}

	/**
	 * TODO Implement this method.
	 */
	private static Points calculateThreeConsecutiveCardsScore(final Trick trick) {
		Points score = new Points();

		return score;
	}

	private static Points calculateStukScore(final Trick trick, final Suit trump) {
		Points score = new Points();

		boolean queen = false;
		boolean king = false;
		for(Card trickCard : trick.getCards()) {
			Class cardSuit = trickCard.getSuit().getClass();
			Rank cardRank = trickCard.getRank();
			if(cardSuit.isInstance(trump)) {
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
