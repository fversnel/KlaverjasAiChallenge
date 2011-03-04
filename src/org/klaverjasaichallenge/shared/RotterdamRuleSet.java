/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 *
 * @author Frank Versnel
 */
public class RotterdamRuleSet implements RuleSet {
	private static final int NUMBER_OF_ROUNDS = 16;

	@Override
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final List<Card> playerHand) {
		boolean result = true;

		final Suit cardSuit = cardToCheck.getSuit();
		final Suit trump = trick.getTrump();
		final Suit leadingSuit = trick.getLeadingSuit();
		if(leadingSuit == null) {
			result = true;
		} else if(Card.hasSuit(leadingSuit, playerHand)) {
			result = cardSuit.equals(leadingSuit);

			// If the leading suit is trump we need to perform an extra
			// check.
			if(leadingSuit.equals(trump)) {
				result = this.playsTrumpRight(trick, playerHand, cardToCheck);
			}
		}
		// Player doesn't have the leading suit but might be able to play
		// trump. If he does have trump, he has to play it.
		else if(Card.hasSuit(trump, playerHand)) {
			result = this.playsTrumpRight(trick, playerHand, cardToCheck);
		}

		return result;
	}

	@Override
	public int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}

	private boolean playsTrumpRight(final Trick trick,
			final List<Card> playerHand, final Card cardToCheck) {
		boolean result = true;
		final Suit trump = trick.getTrump();

		if(Card.hasSuit(trump, playerHand)) {
			if(this.playerCanOverTrump(trick, playerHand)) {
				final Suit leadingSuit = trick.getLeadingSuit();
				result = cardToCheck.isHigherThan(trump, leadingSuit,
						trick.getHighestCard());
			} else {
				final Suit cardSuit = cardToCheck.getSuit();
				result = cardSuit.equals(trump);
			}
		}

		return result;
	}

	private boolean playerCanOverTrump(final Trick trick,
			final List<Card> playerHand) {
		boolean result = false;

		final Card highestTrumpTrick = trick.getHighestTrump();

		final Suit trump = trick.getTrump();
		final Suit leadingSuit = trick.getLeadingSuit();
		final Card highestTrumpPlayer = Card.highestTrump(trump, playerHand);

		if(highestTrumpTrick != null && highestTrumpPlayer != null) {
			result = highestTrumpPlayer.isHigherThan(trump, leadingSuit,
					highestTrumpTrick);
		}

		return result;
	}

}

