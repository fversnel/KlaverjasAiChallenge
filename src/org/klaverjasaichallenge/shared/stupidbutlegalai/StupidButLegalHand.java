package org.klaverjasaichallenge.shared.stupidbutlegalai;

import java.util.LinkedList;
import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class StupidButLegalHand {
	private List<Card> cards;

	public StupidButLegalHand(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * This method removes the cards from the list that the player may not
	 *
	 * @param trick
	 * @param trump
	 */
	public void removeCardsThatAreIllegal(Trick trick, Suit trump) {
		Suit leadingSuit = trick.getLeadingSuit();

		// If there is a leading suit, i can not play whatever i want :\\\\
		if (leadingSuit != null) {
			// If I can follow suit
			if (canFollowSuit(leadingSuit)) {
				removeCardsNotFromSuit(leadingSuit);
			} else {
				// If its possible to play a trump card
				if (canFollowSuit(trump)) {
					removeCardsNotFromSuit(trump);

					Rank highestTrumpOnTable = this.getHighestTrump(trick.getCards(), trump);

					// Its possible to raise the trump, remove all lower trumps
					if(highestTrumpOnTable != null && this.getHighestTrump(this.cards, trump).getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())) {
						this.removeLowerTrumpCards(highestTrumpOnTable, trump);
					}
				}
			}
		}
	}

	private void removeLowerTrumpCards(Rank highestTrumpOnTable, Suit trump) {
		List<Card> handCopy = new LinkedList<Card>(this.cards);

		for(Card card : handCopy)
			if(card.getSuit().equals(trump) && highestTrumpOnTable.getTrumpOrder().isHigherThan(card.getTrumpOrder()))
				this.cards.remove(card);
	}

	private Rank getHighestTrump(List<Card> cards, Suit trump) {
		Rank highestTrumpOnTable = null;

		for(Card card : cards) {
			if(card.getSuit().equals(trump) && (highestTrumpOnTable == null || card.getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())))
				highestTrumpOnTable = card.getRank();
		}

		return highestTrumpOnTable;
	}

	private void removeCardsNotFromSuit(Suit suit) {
		List<Card> cardsCopy = new LinkedList<Card>(this.cards);
		for (Card card : cardsCopy)
			if (!card.getSuit().equals(suit))
				this.cards.remove(card);
	}

	private boolean canFollowSuit(Suit leadingSuit) {
		for (Card card : this.cards)
			if (card.getSuit().equals(leadingSuit))
				return true;

		return false;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	@Override
	public StupidButLegalHand clone() {
		return new StupidButLegalHand(new LinkedList<Card>(this.cards));
	}
}
