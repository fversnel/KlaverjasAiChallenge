package org.klaverjasaichallenge.ai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 * A simple AI that demonstrates the basic principles of how to create
 * your own Klaverjas AI. This AI can also be used for testing purposes,
 * for example for using of playing against your own AI.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class StupidButLegalAI extends KlaverJasAI {
	private RuleSet ruleSet;
	private Hand hand;
	private List<Card> cardsPlayed;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyRuleset(final RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	/**
	 * {@inheritDoc}
	 * Just play any legal card.
	 */
	@Override
	public Card playCard(Trick trick) {
		final Hand legalCards = this.ruleSet.getLegalCards(trick, this.hand);

		final Card playedCard = legalCards.getCards().remove(0);
		this.hand.drawCard(playedCard);

		return playedCard;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveCards(Hand cards) {
		this.hand = cards;

		// Empty the list of cards played, because its a new round
		this.cardsPlayed = new LinkedList<Card>();
	}

	/**
	 * {@inheritDoc}
	 * We don't care about this, just always play on the trump.
	 */
	@Override
	public boolean playsOnTrump(Suit trump, int turn) {
		// Don't care, just always play on trump for convenience.
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyEndOfTrick(Trick trick) {
		this.cardsPlayed.addAll(trick.getCards());
	}

	/**
	 * {@inheritDoc}
	 * We don't care about this.
	 */
	@Override
	public void notifyStartOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1, int enemy2) {
		// Don't care.
	}

	/**
	 * @return StupidButLegalAI(hashcode)
	 */
	@Override
	public String toString() {
		return String.format("StupidButLegalAI [%d]", this.hashCode());
	}

}
