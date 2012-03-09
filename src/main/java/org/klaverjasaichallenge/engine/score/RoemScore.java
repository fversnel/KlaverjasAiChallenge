
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
	private static final Points FOUR_JACKS = new Points(100);
	private static final Points FOUR_CARDS_SAME_RANK = new Points(100);
	private static final Points FOUR_CONSECUTIVE_CARDS = new Points(50);
	private static final Points THREE_CONSECUTIVE_CARDS = new Points(20);
	private static final Points STUK = new Points(20);

	private static final Order FIRST_HIGH_CARD = new Order(4);
	private static final Order JACK_ORDER = Rank.JACK.getRoemOrder();

	public static Points calculateScore(final Trick trick) {
		Points score = Points.ZERO();

		score = score.plus(calculateFourCardsSameRankScore(trick));
		score = score.plus(calculateConsecutiveCardsScore(trick));
		score = score.plus(calculateStukScore(trick));

		return score;
	}

	private static Points calculateFourCardsSameRankScore(final Trick trick) {
		final Card firstCard = trick.getCards().get(0);
		final boolean fourCardsSameRank = Iterables.all(trick.getCards(), new Predicate<Card>() {
			public boolean apply(final Card card) {
				return card.hasSameRankAs(firstCard);
			}
		});

		Points score = Points.ZERO();

		final Order cardOrder = firstCard.getRoemOrder();
		if(cardOrder.isHigherOrSameAs(FIRST_HIGH_CARD) && fourCardsSameRank) {
			score = FOUR_CARDS_SAME_RANK;

			if(cardOrder.equals(JACK_ORDER)) {
				score = score.plus(FOUR_JACKS);
			}
		}

		return score;
	}

	private static Points calculateConsecutiveCardsScore(final Trick trick) {
		Points score = Points.ZERO();

		for(final Card card : trick.getCards()) {
			boolean oneRankDifference = false;
			boolean twoRanksDifference = false;
			boolean threeRanksDifference = false;

			for(final Card otherCard : trick.getCards()) {
				final Order orderDifference = card.getRoemOrder().minus(
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
			} else if((oneRankDifference && twoRanksDifference) &&
					THREE_CONSECUTIVE_CARDS.biggerThan(score)) {
				score = THREE_CONSECUTIVE_CARDS;
			}
		}

		return score;
	}

	private static Points calculateStukScore(final Trick trick) {
		final List<Card> cards = trick.getCards();
		final Suit trump = trick.getTrump();

		boolean queenAvailable = cards.contains(Card.get(trump, Rank.QUEEN));
		boolean kingAvailable = cards.contains(Card.get(trump, Rank.KING));

		return kingAvailable && queenAvailable ? STUK : Points.ZERO();
	}

}
