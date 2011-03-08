package org.klaverjasaichallenge.ai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class StupidButLegalHand {
	private Hand hand;

	public StupidButLegalHand(Hand hand) {
		this.hand = hand;
	}

	/**
	 * This method removes the cards from the list that the player may not
	 * play during this {@link Trick}.
	 *
	 * @param trick the current trick.
	 */
	public List<Card> getLegalCards(final RuleSet ruleSet, final Trick
			trick) {
		List<Card> legalCards =  new LinkedList<Card>();
		for(final Card card : this.hand.getCards()) {
			if(ruleSet.isLegalCard(trick, card, this.hand)) {
				legalCards.add(card);
			}
		}

		assert(legalCards.size() > 0);

		return legalCards;
	}

	public List<Card> getCards() {
		return this.hand.getCards();
	}

	public void removeCard(final Card card) {
		this.hand.drawCard(card);
	}

}
