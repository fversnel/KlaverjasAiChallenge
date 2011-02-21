/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public class RotterdamRuleSet extends RuleSet {
	private static final int NUMBER_OF_ROUNDS = 16;

	@Override
	public boolean isLegalCard(final org.klaverjasaichallenge.server.round.Trick trick, final Card cardToCheck,
			final List<Card> playerHand) {
		return this.isLegalCard(new Trick(trick), cardToCheck, playerHand);
	}

	/**
	 * @trick the trick on which the {@link cardToCheck} should be played.
	 * @cardToCheck the card to be played.
	 * @playerHand all cards that are currently in the player's hand,
	 * including {@link cardToCheck}.
	 */
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

	protected boolean playsTrumpRight(final Trick trick,
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
		boolean result = true;
		final Card highestTrumpTrick = trick.getHighestTrump();

		if(highestTrumpTrick != null) {
			final Suit trump = trick.getTrump();
			final Suit leadingSuit = trick.getLeadingSuit();
			final Card highestTrumpPlayer = Card.highestTrump(trump, playerHand);
			result = highestTrumpPlayer != null &&
				highestTrumpPlayer.isHigherThan(trump, leadingSuit, highestTrumpTrick);
		}

		return result;
	}

}

