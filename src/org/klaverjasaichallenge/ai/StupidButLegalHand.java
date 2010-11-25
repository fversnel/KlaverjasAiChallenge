package org.klaverjasaichallenge.ai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class StupidButLegalHand {
	private List<Card> cards;

	public StupidButLegalHand(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * This method removes the cards from the list that the player may not
	 *
	 * @param trick
	 * @param trump
	 */
	public List<Card> getLegalCards(final RuleSet ruleSet, final Trick
			trick, final Player player, final Suit trump) {
		// Copy the list of cards to prevent a concurrent modification
		// exception.
		List<Card> legalCards =  new LinkedList<Card>();
		for(final Card card : this.cards) {
			if(ruleSet.isLegalCard(trick, card, player, this.cards, trump)) {
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
