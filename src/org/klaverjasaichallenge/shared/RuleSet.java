/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public abstract class RuleSet {

	/**
	 * @trick the trick on which the {@link cardToCheck} should be played.
	 * @cardToCheck the card to be played.
	 * @playerHand all cards that are currently in the player's hand,
	 * including {@link cardToCheck}.
	 *
	 * @since 1.3
	 */
	public abstract boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final List<Card> playerHand);

	public abstract int getNumberOfRounds();

	/**
	 * Checks whether a player has the ability to play a card of some suit
	 *
	 * @param player
	 * @param leadingSuit
	 * @return true when the player can follow suit, false when not
	 */
	protected boolean playerCanFollowSuit(final List<Card> playerHand,
			final Suit suitToCheck) {
		return Card.hasSuit(suitToCheck, playerHand);
	}

}
