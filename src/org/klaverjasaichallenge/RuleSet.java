/**
 * 
 */
package org.klaverjasaichallenge;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public abstract class RuleSet {
	
	public abstract void checkCardLegitimacy(final Trick trick, final Card cardToCheck,
			final Player player, final Hand playerHand, final Suit trump) throws CheatException;

	public abstract int getNumberOfRounds();

	/**
	 * Checks whether a player has the ability to play a card of some suit
	 *
	 * @param player
	 * @param leadingSuit
	 * @return true when the player can follow suit, false when not
	 */
	protected boolean playerCanFollowSuit(final Hand playerHand, 
			final Suit suitToCheck) {
		return playerHand.hasSuit(suitToCheck);
	}
	
}
