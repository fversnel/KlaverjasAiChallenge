package org.klaverjasaichallenge;

import java.util.List;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.RuleSet;

public class FakePlayer implements Player {

	public FakePlayer() {
	}

	@Override
	public void giveRuleSet(final RuleSet ruleSet) {
	}

	@Override
	public void giveCards(List<Card> cards) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean playOnTrump(Suit trump, Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card getCard(Trick trick, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endOfTrick(Trick trick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1, int enemy2) {
		// TODO Auto-generated method stub

	}
}
