/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;

import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public class RotterdamRuleSet extends RuleSet {
	private static final int NUMBER_OF_ROUNDS = 16;

	private final Logger logger;

	public RotterdamRuleSet() {
		this.logger = Logger.getLogger("KlaverjasLogger");
	}

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
					// If the player is playing a trump card
					if (cardToCheck.getSuit().equals(trump)) {
						Rank highestTrumpOnTable = trick.getHighestTrump(trump);
						// If the player is not raising a trump, but is able
						// to (playerCanRaiseTrump()), throw exception
						if (highestTrumpOnTable != null
								&& highestTrumpOnTable.getTrumpOrder().isHigherThan(cardToCheck.getRank().getTrumpOrder())
								&& playerCanRaiseTrump(highestTrumpOnTable, playerHand, trump)) {
							result = false;
							logger.info("Player " + player + " can raise the trump but is not doing it."
									+ " Current trump: " + trump + ". Card played: " + cardToCheck);
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump(), throw exception
					else if (playerCanFollowSuit(playerHand, trump)) {
						result = false;
						logger.info("Player " + player
								+ " has trump but is not playing it. Current trump: " + trump + ". Card played: "
								+ cardToCheck);
					}
				}
				// Else - Player can follow suit but is not, throw exception
				else {
					result = false;
					logger.info("Player " + player + " can follow suit but is not. Current suit: "
							+ leadingSuit + ".  Card played: " + cardToCheck);
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
		final Rank highestTrumpOfPlayer = Card.highestTrump(trump, playerHand).getRank();

		if (highestTrumpOfPlayer != null &&
				highestTrumpOfPlayer.getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())) {
			result = true;
		}

		return result;
	}

}

