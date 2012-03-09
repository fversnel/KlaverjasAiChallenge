/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import com.google.common.base.Optional;

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
	protected final static int NUMBER_OF_ROUNDS = 16;

	@Override
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final Hand playerHand) {
		boolean result = true;

		final Suit trump = trick.getTrump();
		final Optional<Suit> leadingSuit = Optional.fromNullable(trick.getLeadingSuit());

		if(!leadingSuit.isPresent()) {
			result = true;
		} else if(playerHand.hasSuit(leadingSuit.get())) {
			result = cardToCheck.isOfSuit(leadingSuit.get());

			// If the leading suit is trump we need to perform an extra
			// check.
			if(leadingSuit.get().equals(trump)) {
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
				result = cardToCheck.isOfSuit(trump);
			}
		}

		return result;
	}

	private boolean canOverTrump(final Trick trick, final Hand playerHand) {
		boolean result = false;

		final Optional<Card> highestTrumpTrick = Optional.fromNullable(trick.getHighestTrump());
		final Optional<Card> highestTrumpPlayer = Optional.fromNullable(playerHand.highestTrump(trick));

		if(highestTrumpTrick.isPresent() && highestTrumpPlayer.isPresent()) {
			result = highestTrumpPlayer.get().isHigherThan(trick, highestTrumpTrick.get());
		}

		return result;
	}

}

