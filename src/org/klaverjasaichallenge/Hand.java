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
		for(Card cardInHand : cards) {
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

	public int amountOfCards() {
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

	/**
	 * TODO This method belongs elsewhere
	 * This function checks whether a player can play a card of
	 * given suit or not.
	 * 
	 * @param player
	 * @param suit
	 * @return True if the player can follow suit
	 */
	//private boolean playerCanFollowSuit(final Player player, final Suit suit) {
	//	Hand playerHand = hands.get(player);
	//	return playerHand.hasSuit(suit);
	//}
	
	/**
	 * TODO This method belongs elsewhere
	 * This function checks whether a player can play a trump card
	 * 
	 * @param player
	 * @return True if the player has a trump card
	 */
	//private boolean playerHasTrump(Player player) {
	//	Hand playerHand = hands.get(player);
	//	return playerHand.hasSuit(this.trump);
	//}	
	
	/**
	 * This function checks whether a player can play a trump card
	 * that is higher then the current played trumps.
	 * 
	 * @param player
	 * @return True if the player can raise the trump
	 * 
	 * TODO Raise trump might not be the perfect naming ...
	 */
	//private boolean playerCanRaiseTrump(final Suit trump) {
	//	Rank highestTrumpOnTable = getHighestTrump();
	//	// Loop through all cards of the player
	//	for (Card card : this.cards) {
	//		// If the player has a class of the same suit as the trump suit
	//		if (card.getSuit().equals(trump) 
	//				&& (highestTrumpOnTable == null 
	//				|| card.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder()))) {
	//			// Player can raise trump
	//			return true;
	//		}
	//	}

	//	// All cards have been checked but none found of trump suit, player
	//	// has no trump cards
	//	return false;
	//}

	
		
}
