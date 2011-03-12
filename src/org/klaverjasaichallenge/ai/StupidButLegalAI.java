package org.klaverjasaichallenge.ai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class StupidButLegalAI extends KlaverJasAI {
	private RuleSet ruleSet;
	private Hand hand;
	private List<Card> cardsPlayed;

	@Override
	public void giveRuleSet(final RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	@Override
	public Card getCard(Trick trick) {
		final Hand legalCards = this.ruleSet.getLegalCards(trick, this.hand);

		final Card playedCard = legalCards.getCards().remove(0);
		this.hand.drawCard(playedCard);

		return playedCard;
	}

	@Override
	public void giveCards(Hand cards) {
		this.hand = cards;

		// Empty the list of cards played, because its a new round
		this.cardsPlayed = new LinkedList<Card>();
	}

	@Override
	public boolean playOnTrump(Suit trump, Order order) {
		// Don't care, just always play on trump for convenience.
		return true;
	}

	@Override
	public void endOfTrick(Trick trick) {
		this.cardsPlayed.addAll(trick.getCards());
	}

	@Override
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1, int enemy2) {
		// Don't care.
	}

	@Override
	public String toString() {
		return "StupidButLegalAI (" + this.hashCode() + ")";
	}

}
