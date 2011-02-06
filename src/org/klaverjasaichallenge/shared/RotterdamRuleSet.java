/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public class RotterdamRuleSet extends RuleSet {
	private static final int NUMBER_OF_ROUNDS = 16;

	public RotterdamRuleSet() {
	}
	
	@Override
	public boolean isLegalCard(final org.klaverjasaichallenge.server.Trick trick, final Card cardToCheck,
			final List<Card> playerHand) {
		return this.isLegalCard(new Trick(trick), cardToCheck, null, playerHand, trick.getTrump());
	}
	
	@Override
	@Deprecated
	public boolean isLegalCard(final org.klaverjasaichallenge.server.Trick trick, final Card cardToCheck,
			final Player player, final List<Card> playerHand, final Suit trump) {
		return this.isLegalCard(new Trick(trick), cardToCheck, null, playerHand, trick.getTrump());
	}

	@Override
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final List<Card> playerHand) {
		return this.isLegalCard(trick, cardToCheck, null, playerHand, trick.getTrump());
	}

	/**
	 * The player object is now useless here.
	 */
	@Deprecated
	@Override
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final Player player, final List<Card> playerHand, final Suit trump) {
		boolean result = true;

		// Find out current suit
		Suit leadingSuit = trick.getLeadingSuit();

		// If the leading player has already played a card (== first card)
		if (leadingSuit != null) {
			// If the player is not following suit
			if (!cardToCheck.getSuit().equals(leadingSuit)) {
				// If the player cannot follow suit (!playerCanFollowSuit())
				if (!this.playerCanFollowSuit(playerHand, leadingSuit)) {
					// If the player is playing a trump card:
					if (cardToCheck.getSuit().equals(trump)) {
						Rank highestTrumpOnTable = trick.getHighestTrump();
						// If the player is not raising a trump, but is able
						// to (playerCanRaiseTrump()).
						if (highestTrumpOnTable != null
								&& highestTrumpOnTable.getTrumpOrder().isHigherThan(cardToCheck.getRank().getTrumpOrder())
								&& playerCanRaiseTrump(highestTrumpOnTable, playerHand, trump)) {
							result = false;
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump()).
					else if (playerCanFollowSuit(playerHand, trump)) {
						result = false;
					}
				}
				// Else - Player can follow suit but is not.
				else {
					result = false;
				}
			}
		}

		return result;
	}

	@Override
	public int getNumberOfRounds() {
		return NUMBER_OF_ROUNDS;
	}

	/**
	 * Checks whether an player can play a card that is higher then the
	 * currently highest ranked trump played.
	 *
	 * @return True if the player is able to, false if not
	 */
	private boolean playerCanRaiseTrump(final Rank highestTrumpOnTable,
			final List<Card> playerHand, final Suit trump) {
		boolean result = false;
		final Card highestTrumpOfPlayer = Card.highestTrump(trump, playerHand);

		if (highestTrumpOfPlayer != null &&
				highestTrumpOfPlayer.getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())) {
			result = true;
		}

		return result;
	}

}

