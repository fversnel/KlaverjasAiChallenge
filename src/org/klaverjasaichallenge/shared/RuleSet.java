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
 * @author Joost
 *
 */
public abstract class RuleSet {

	/**
	 * @since 1.3
	 */
	public abstract boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final List<Card> playerHand);

	/**
	 * @deprecated too many arguments, only trick, cardToCheck and playerHand
	 * are needed.
	 * @since 1.2
	 */
	@Deprecated
	public abstract boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final Player player, final List<Card> playerHand, final Suit trump);

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
