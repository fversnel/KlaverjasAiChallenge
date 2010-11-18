package org.klaverjasaichallenge.ai;


import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


public class StupidButLegalAI implements KlaverJasAI {

	private StupidButLegalHand hand;
	private List<Card> cardsPlayed;
	private Suit trump;

	// TODO Place this in Order
	private final Order FIRST = new Order(0);
	private final Order SECOND = new Order(1);
	private final Order THIRD = new Order(2);
	private final Order FOURTH = new Order(3);

	@Override
	public Card getCard(Trick trick, Order order) {

		/**
		 * Step 1: Remove Illegal Cards
		 */
		StupidButLegalHand roundHand = this.hand.clone();
		roundHand.removeCardsThatAreIllegal(trick, this.trump);

		List<Card> legalCards = roundHand.getCards();
		List<Card> allCards = this.hand.getCards();

		/**
		 * Step 2: ....
		 */


		/**
		 * Step 3: Profit
		 */
		Card playedCard = legalCards.remove(0);
		allCards.remove(playedCard);

		return playedCard;
	}

	@Override
	public void giveCards(List<Card> hand) {
		this.hand = new StupidButLegalHand(hand);

		// Empty list of cards played, because its a new round
		this.cardsPlayed = new LinkedList<Card>();
	}

	@Override
	public boolean playOnTrump(Suit suit, Order order) {
		return true;
	}

	@Override
	public String toString() {
		return "StupidButLegalAI (" +this.hashCode() + ")";
	}


	public static void main(String[] args) {

	}

	@Override
	public void endOfTrick(Trick trick) {
		for(Card card : trick.getCards())
			this.cardsPlayed.add(card);
	}

	@Override
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1, int enemy2) {
		this.trump = trump;
		
	}
}
