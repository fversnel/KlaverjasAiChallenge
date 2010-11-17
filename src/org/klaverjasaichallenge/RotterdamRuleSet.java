/**
 * 
 */
package org.klaverjasaichallenge;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

import org.klaverjasaichallenge.shared.CheatException;

/**
 * @author Joost
 *
 */
public class RotterdamRuleSet extends RuleSet {

	public RotterdamRuleSet() {
	}

	// legitimacy
	public void checkCardLegitimacy(final Trick trick, final Card cardToCheck,
			final Player player, final Hand playerHand, final Suit trump) throws CheatException {
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
							throw new CheatException("Player " + player + " can raise the trump but is not doing it."
									+ " Current trump: " + trump + ". Card played: " + cardToCheck);
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump(), throw exception
					else if (playerCanFollowSuit(playerHand, trump)) {
						throw new CheatException("Player " + player
								+ " has trump but is not playing it. Current trump: " + trump + ". Card played: "
								+ cardToCheck);
					}
				}
				// Else - Player can follow suit but is not, throw exception
				else {
					throw new CheatException("Player " + player + " can follow suit but is not. Current suit: "
							+ leadingSuit + ".  Card played: " + cardToCheck);
				}
			}
		}
	}
	
	/**
	 * Checks whether an player can play a card that is higher then the
	 * currently highest ranked trump played.
	 *
	 * @return True if the player is able to, false if not
	 */
	private boolean playerCanRaiseTrump(final Rank highestTrumpOnTable, 
			final Hand playerHand, final Suit trump) {
		boolean result = false;
		final Rank highestTrumpOfPlayer = playerHand.getHighestTrump(trump);

		if (highestTrumpOfPlayer != null && 
				highestTrumpOfPlayer.getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())) {
			result = true;
		}
		
		return result;
	}

}

