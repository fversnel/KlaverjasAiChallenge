package org.klaverjasaichallenge.ai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;

public class StupidButLegalHand {
	private List<Card> cards;

	public StupidButLegalHand(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * This method removes the cards from the list that the player may not
	 *
	 * @param trick
	 */
	public List<Card> getLegalCards(final RuleSet ruleSet, final Trick
			trick) {
		// Copy the list of cards to prevent a concurrent modification
		// exception.
		List<Card> legalCards =  new LinkedList<Card>();
		for(final Card card : this.cards) {
			if(ruleSet.isLegalCard(trick, card, this.cards)) {
				legalCards.add(card);
			}
		}

		assert(legalCards.size() > 0);

		return legalCards;
	}

	public List<Card> getCards() {
		return this.cards;
	}

}
