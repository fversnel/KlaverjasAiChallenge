/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;


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
			final Hand playerHand);

	public Hand getLegalCards(final Trick trick, final Hand hand) {
		List<Card> legalCards =  new LinkedList<Card>();
		for(final Card card : hand.getCards()) {
			if(this.isLegalCard(trick, card, hand)) {
				legalCards.add(card);
			}
		}

		assert(legalCards.size() > 0);

		return new Hand(legalCards);
	}

	public abstract int getNumberOfRounds();

}
