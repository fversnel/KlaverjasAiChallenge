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
	private StupidButLegalHand hand;
	private List<Card> cardsPlayed;

	@Override
	public void giveRuleSet(final RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	@Override
	public Card getCard(Trick trick) {
		List<Card> legalCards = this.hand.getLegalCards(this.ruleSet, trick);

		Card playedCard = legalCards.remove(0);
		this.hand.removeCard(playedCard);

		return playedCard;
	}

	@Override
	public void giveCards(Hand cards) {
		this.hand = new StupidButLegalHand(cards);

		// Empty the list of cards played, because its a new round
		this.cardsPlayed = new LinkedList<Card>();
	}

	@Override
	public boolean playOnTrump(Suit trump, Order order) {
		return true;
	}

	@Override
	public void endOfTrick(Trick trick) {
		this.cardsPlayed.addAll(trick.getCards());
	}

	@Override
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1, int enemy2) {
		// Don't care
	}

	@Override
	public String toString() {
		return "StupidButLegalAI (" + this.hashCode() + ")";
	}

}
