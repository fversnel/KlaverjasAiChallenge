package org.klaverjasaichallenge;

import java.util.List;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class FakePlayer implements Player {

	public FakePlayer() {
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
	public Card getCard(Trick trick, Suit trump, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endOfTrick(Trick trick) {
		// TODO Auto-generated method stub
		
	}
}
