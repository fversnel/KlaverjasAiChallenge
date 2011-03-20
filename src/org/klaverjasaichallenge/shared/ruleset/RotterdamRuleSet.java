/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import org.klaverjasaichallenge.shared.ruleset.RuleSet;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class RotterdamRuleSet extends RuleSet {
	protected static final int NUMBER_OF_ROUNDS = 16;

	@Override
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final Hand playerHand) {
		boolean result = true;

		final Suit trump = trick.getTrump();
		final Suit leadingSuit = trick.getLeadingSuit();
		if(leadingSuit == null) {
			result = true;
		} else if(playerHand.hasSuit(leadingSuit)) {
			final Suit cardSuit = cardToCheck.getSuit();
			result = cardSuit.equals(leadingSuit);

			// If the leading suit is trump we need to perform an extra
			// check.
			if(leadingSuit.equals(trump)) {
				result = this.playsTrumpRight(trick, playerHand, cardToCheck);
			}
		}
		// Player doesn't have the leading suit but might be able to play
		// trump. If he does have trump, he has to play it.
		else if(playerHand.hasSuit(trump)) {
			result = this.playsTrumpRight(trick, playerHand, cardToCheck);
		}

		return result;
	}

	@Override
	public int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}

	private boolean playsTrumpRight(final Trick trick,
			final Hand playerHand, final Card cardToCheck) {
		boolean result = true;
		final Suit trump = trick.getTrump();

		if(playerHand.hasSuit(trump)){
			if(this.canOverTrump(trick, playerHand)) {
				result = cardToCheck.isHigherThan(trick,
						trick.getHighestCard());
			} else {
				final Suit cardSuit = cardToCheck.getSuit();
				result = cardSuit.equals(trump);
			}
		}

		return result;
	}

	private boolean canOverTrump(final Trick trick, final Hand playerHand) {
		boolean result = false;

		final Card highestTrumpTrick = trick.getHighestTrump();

		final Card highestTrumpPlayer = playerHand.highestTrump(trick);

		if(highestTrumpTrick != null && highestTrumpPlayer != null) {
			result = highestTrumpPlayer.isHigherThan(trick,
					highestTrumpTrick);
		}

		return result;
	}

}

