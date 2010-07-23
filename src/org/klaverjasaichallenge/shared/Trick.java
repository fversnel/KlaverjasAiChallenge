
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

	private Map<Player, Card> cards;
	private Suit leadingSuit;

	public Trick() {
		this.cards = new HashMap<Player, Card>();
		this.leadingSuit = null;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(player, cardPlayed);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = cardPlayed.getSuit();
		}
	}	

	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards.values());
	}

	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

	public Points getStockScore(final Suit trump) {
		return Score.calculateStockScore(this, trump);
	}

	public Points getRoemScore(final Suit trump) {
		return Score.calculateRoemScore(this, trump);
	}

	public Points getTotalScore(final Suit trump) {
		Points score = new Points();

		score = this.getStockScore(trump);
		score = Points.plus(score, this.getRoemScore(trump));

		return score;
	}

	/**
	 * @return
	 */
	private Rank getHighestTrump() {
		Rank highestTrumpOnTable = null;
		// Loop through the currently played cards
		for(Card cardOnTable : this.cardsOnTable.getCards()) {
			// If this card is a trump and higher ranked then current top ranked trump
			if (cardOnTable.getSuit().equals(this.trump) &&
					(highestTrumpOnTable == null ||
					cardOnTable.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder()))) {
				highestTrumpOnTable = cardOnTable.getRank();
			}
		}

		return highestTrumpOnTable;
	}
}
