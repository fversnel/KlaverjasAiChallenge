
package org.klaverjasaichallenge.engine.score;

import java.util.List;
import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Order;
import org.klaverjasaichallenge.shared.card.Rank;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
class RoemScore {
	private static final Points FOUR_JACKS = new Points(200);
	private static final Points FOUR_CARDS_SAME_RANK = new Points(100);
	private static final Points FOUR_CONSECUTIVE_CARDS = new Points(50);
	private static final Points THREE_CONSECUTIVE_CARDS = new Points(20);
	private static final Points STUK = new Points(20);

	private static final Order FIRST_HIGH_CARD = new Order(4);
	private static final Order JACK_ORDER = Rank.JACK.getRoemOrder();

	public static Points calculateScore(final Trick trick) {
		Points score = new Points();

		score = Points.plus(score, calculateFourCardsSameRankScore(trick));
		score = Points.plus(score, calculateConsecutiveCardsScore(trick));
		score = Points.plus(score, calculateStukScore(trick, trick.getTrump()));

		return score;
	}

	private static Points calculateFourCardsSameRankScore(final Trick trick) {
		Points score = new Points();

		final List<Card> cards = trick.getCards();
		final Card firstCard = cards.get(0);
		final Order cardOrder = firstCard.getRoemOrder();
		if(cardOrder.isHigherOrSameAs(FIRST_HIGH_CARD)) {
			boolean fourCardsSameRank = true;

			for(Card currentCard : cards) {
				if(!(currentCard.hasSameRankAs(firstCard))) {
					fourCardsSameRank = false;
				}
			}

			if(fourCardsSameRank) {
				if(cardOrder.equals(JACK_ORDER)) {
					score = FOUR_JACKS;
				} else {
					score = FOUR_CARDS_SAME_RANK;
				}
			}
		}

		return score;
	}

	private static Points calculateConsecutiveCardsScore(final Trick trick) {
		Points score = new Points();

		for(final Card card : trick.getCards()) {
			boolean oneRankDifference = false;
			boolean twoRanksDifference = false;
			boolean threeRanksDifference = false;

			for(final Card otherCard : trick.getCards()) {
				final Order orderDifference = Order.minus(card.getRoemOrder(),
						otherCard.getRoemOrder());
				if(card.hasSameSuitAs(otherCard)) {
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
		boolean queenAvailable = Iterables.any(trick.getCards(), new IsCard(trump, Rank.QUEEN));
		boolean kingAvailable = Iterables.any(trick.getCards(), new IsCard(trump, Rank.KING));

		return kingAvailable && queenAvailable ? STUK : new Points();
	}

	private static class IsCard implements Predicate<Card> {
		private final Suit suit;
		private final Rank rank;

		public IsCard(final Suit suit, final Rank rank) {
			this.suit = suit;
			this.rank = rank;
		}

		public boolean apply(Card card) {
			return card.isOfSuit(suit) && card.isOfRank(rank);
		}
	};

}
