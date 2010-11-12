package org.klaverjasaichallenge.shared.stupidbutlegalai;


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
	
	// TODO Place this in Order
	private final Order FIRST = new Order(0);
	private final Order SECOND = new Order(1);
	private final Order THIRD = new Order(2);
	private final Order FOURTH = new Order(3);
	
	@Override
	public Card getCard(Trick trick, Suit trump, Order order) {
		
		/**
		 * Step 1: Remove Illegal Cards
		 */
		StupidButLegalHand roundHand = hand.clone();
		roundHand.removeCardsThatAreIllegal(trick, trump);

		List<Card> legalCards = roundHand.getCards();
		List<Card> allCards = this.hand.getCards();
		
		/**
		 * Step 2: ....
		 */
		
		if(order.equals(FIRST)) {
			
		}
		
		if(order.equals(SECOND)) {
			
		}
		
		if(order.equals(THIRD)) {
			
		}
		
		if(order.equals(FOURTH)) {
			
		}
		
		/**
		 * LOLKEKZ
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
	
	public String toString() {
		return "StupidButLegalAI (" +this.hashCode() + ")";
	}
	

	public static void main(String[] args) {
		
	}
 
	public void endOfTrick(Trick trick) {
		for(Card card : trick.getCards())
			cardsPlayed.add(card);
	}
}
