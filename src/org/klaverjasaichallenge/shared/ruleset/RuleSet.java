/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import java.util.List;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;


/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public interface RuleSet {

	/**
	 * @trick the trick on which the {@link cardToCheck} should be played.
	 * @cardToCheck the card to be played.
	 * @playerHand all cards that are currently in the player's hand,
	 * including {@link cardToCheck}.
	 *
	 * @since 1.3
	 */
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final Hand playerHand);

	/**
	 * @deprecated This will be removed in 1.5. Use isLegalCard(Trick, Card, Hand) instead.
	 */
	@Deprecated 	
	public boolean isLegalCard(final Trick trick, final Card cardToCheck,
			final List<Card> playerHand);
	
	public int getNumberOfRounds();

}
