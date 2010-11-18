package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Hand {
	private static final int CARDS_IN_HAND = 8;

	private final List<Card> cards;

	public Hand(final Deck deck) {
		this.cards = this.drawHand(deck);
	}

	public boolean hasSuit(final Suit suit) {
		boolean check = false;
		for(Card cardInHand : this.cards) {
			if(cardInHand.getSuit().equals(suit)) {
				check = true;
			}
		}

		return check;
	}
	
	public Rank getHighestTrump(final Suit trump) {
		Rank highestTrump = null;
		// Loop through the currently played cards
		for(Card card : this.cards) {
			// If this card is a trump and higher ranked then current top ranked trump
			if (card.getSuit().equals(trump) &&
					(highestTrump == null ||
							card.getRank().getTrumpOrder().isHigherThan(highestTrump.getTrumpOrder()))) {
				highestTrump = card.getRank();
			}
		}

		return highestTrump;
	}	

	public Card drawCard(final Card card) {
		Card cardInHand = null;
		if(this.cards.remove(card)) {
			cardInHand = card;
		}

		return cardInHand;
	}

	public int getNumberOfCards() {
		return this.cards.size();
	}

	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards);
	}

	private List<Card> drawHand(final Deck deck) {
		List<Card> hand = new LinkedList<Card>();
		for (int i = 1; i <= CARDS_IN_HAND; i++) {
			hand.add(deck.drawCard());
		}

		return hand;
	}
		
}
