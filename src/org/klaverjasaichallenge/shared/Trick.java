
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.score.Score;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	private static final int FIRST_ADDED_CARD = 1;

	private Map<Card, Player> cards;
	private Suit leadingSuit;

	public Trick() {
		this.cards = new HashMap<Card, Player>();
		this.leadingSuit = null;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(cardPlayed, player);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = cardPlayed.getSuit();
		}
	}	

	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards.keySet());
	}

	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

	private Points getStockScore(final Suit trump) {
		return Score.calculateStockScore(this, trump);
	}

	private Points getRoemScore(final Suit trump) {
		return Score.calculateRoemScore(this, trump);
	}
	
	public Score getScore(final Suit trump) {
		return new Score(this.getStockScore(trump),this.getRoemScore(trump));
	}

	public Player getWinner(final Suit trump) {
		Card highestCard = Card.max(trump, new LinkedList<Card>(this.cards.keySet()));
		return this.cards.get(highestCard);
	}

	/**
	 * @return
	 */
	public Rank getHighestTrump(final Suit trump) {
		Rank highestTrumpOnTable = null;
		// Loop through the currently played cards
		for(Card cardOnTable : this.cards.keySet()) {
			// If this card is a trump and higher ranked then current top ranked trump
			if (cardOnTable.getSuit().equals(trump) &&
					(highestTrumpOnTable == null ||
					cardOnTable.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder()))) {
				highestTrumpOnTable = cardOnTable.getRank();
			}
		}

		return highestTrumpOnTable;
	}
	
	public Trick clone() {
		Trick newTrick = new Trick();
		newTrick.cards =  new HashMap<Card, Player>(this.cards);
		newTrick.leadingSuit = this.leadingSuit;
		return newTrick;
	}

}
