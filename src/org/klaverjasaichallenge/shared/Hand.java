package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.Deck;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Hand {
	private static final int CARDS_IN_HAND = 8;

	private Player player;
	private List<Card> cards;

	public Hand(final Player player, final Deck deck) {
		this.player = player;

		this.cards = this.drawHand(deck);
	}

	public boolean hasSuit(final Suit suit) {
		boolean check = false;
		for(Card cardInHand : cards) {
			if(cardInHand.getSuit().equals(suit)) {
				check = true;
			}
		}

		return check;
	}

	public boolean hasCard(final Card card) {
		boolean check = false;
		for(Card cardInHand : cards) {
			if(cardInHand.equals(card)) {
				check = true;
			}
		}

		return check;
	}

	public Card drawCard(final Card card) {
		Card cardInHand = null;
		if(this.cards.remove(card)) {
			cardInHand = card;
		}

		return cardInHand;
	}

	public int amountOfCards() {
		return this.cards.size();
	}

	public List<Card> getCards() {
		return this.cards;
	}

	private List<Card> drawHand(final Deck deck) {
		List<Card> hand = new LinkedList<Card>();
		for (int i = 1; i <= CARDS_IN_HAND; i++) {
			hand.add(deck.drawCard());
		}

		return hand;
	}

}
