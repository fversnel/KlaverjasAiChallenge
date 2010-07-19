
package org.klaverjasaichallenge.score;

import java.util.List;
import java.util.ArrayList;

import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.rank.*;

class SpecialScore {
	private static final Points MARCH = new Points(100);
	private static final Points FOUR_JACKS = new Points(200);
	private static final Points FOUR_CARDS_SAME_RANK = new Points(100);
	private static final Points FOUR_CONSECUTIVE_CARDS = new Points(30);
	private static final Points THREE_CONSECUTIVE_CARDS = new Points(20);
	private static final Points STUK = new Points(20);

	private static final int ALL_TRICKS = 8;

	public static Points calculateScore(final List<Trick> tricks, final Suit trump) {
		Points score = new Points();

		score = calculateMarchScore(tricks);
		score = Points.plus(score, calculateRoemScore(tricks, trump));

		return score;
	}

	private static Points calculateMarchScore(final List<Trick> tricks) {
		Points score = new Points();

		final int amountTricks = tricks.size();
		if(amountTricks == ALL_TRICKS) {
			score = MARCH;
		}

		return score;
	}

	private static Points calculateRoemScore(final List<Trick> tricks, final Suit trump) {
		Points score = new Points();

		for(Trick trick : tricks) {
			Points roem = new Points();
			
			roem = calculateFourCardsSameRankScore(trick);
			roem = Points.plus(roem, calculateFourCardConsecutiveScore(trick));
			roem = Points.plus(roem, calculateThreeCardConsecutiveScore(trick));
			roem = Points.plus(roem, calculateStukScore(trick, trump));

			score = Points.plus(score, roem);
		}

		return score;
	}

	private static Points calculateFourCardsSameRankScore(final Trick trick) {
		Points score = new Points();

		List<Rank> sameRanks = new ArrayList<Rank>();
		for(Card card : trick.getCards()) {
			Rank cardRank = card.getRank();
			if(cardRank instanceof Ace
					|| cardRank instanceof King
					|| cardRank instanceof Queen
					|| cardRank instanceof Jack
					|| cardRank instanceof Ten) {
				sameRanks.add(cardRank);
			}
		}

		boolean fourCardsSameRank = false;
		if(sameRanks.size() == 4) {
			fourCardsSameRank = true;
			for(Rank compareRank : sameRanks) {
				if(!compareRank.getClass().isInstance(sameRanks.get(0))) {
					fourCardsSameRank = false;
				}
			}
		}

		if(fourCardsSameRank) {
			if(sameRanks.get(0) instanceof Jack) {
				score = FOUR_JACKS;
			} else {
				score = FOUR_CARDS_SAME_RANK;
			}
		}

		return score;
	}

	private static Points calculateFourCardConsecutiveScore(final Trick trick) {
		// TODO Implement this method.
		return new Points();
	}

	private static Points calculateThreeCardConsecutiveScore(final Trick trick) {
		// TODO Implement this method.
		return new Points();
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
