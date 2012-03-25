/**
 *
 */
package org.klaverjasaichallenge.shared.ruleset;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
		Iterable<Card> legalCards = Iterables.filter(hand.getCards(), new Predicate<Card>() {
			public boolean apply(final Card card) {
				return isLegalCard(trick, card, hand);
			}
		});

		assert(!Iterables.isEmpty(legalCards));

		return new Hand(Lists.newArrayList(legalCards));
	}

	public abstract int getNumberOfRounds();

}
