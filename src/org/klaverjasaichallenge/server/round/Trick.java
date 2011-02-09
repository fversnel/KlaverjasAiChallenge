
package org.klaverjasaichallenge.server.round;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	public static final int TRICK_COUNT = 8;

	private static final int FIRST_ADDED_CARD = 1;
	private static final int TOTAL_CARDS = 4;

	private Map<Card, Player> cards;
	private Suit trump;
	private Suit leadingSuit;

	public Trick(final Suit trump) {
		this.cards = new HashMap<Card, Player>();
		this.trump = trump;
		this.leadingSuit = null;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(cardPlayed, player);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = cardPlayed.getSuit();
		}

		assert(this.leadingSuit != null);
		assert(this.numberOfCards() <= TOTAL_CARDS) : "Trick cannot contain more than " + TOTAL_CARDS + ".";
	}

	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards.keySet());
	}

	public int numberOfCards() {
		return this.cards.size();
	}

	public Map<Card, Player> getCardsWithPlayers() {
		return this.cards;
	}

	public Suit getTrump() {
		return this.trump;
	}

	/**
	 * @return null if the trick has no cards.
	 */
	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

	public Score getScore() {
		return new Score(this);
	}

	/**
	 * @return null if there is no card played yet.
	 */
	public Player getWinner() {
		assert(!this.cards.keySet().isEmpty()) : "If no cards are played yet, no winner can be determined yet.";

		final Card highestCard = Card.max(this.trump, this.leadingSuit, new LinkedList<Card>(this.cards.keySet()));
		return this.cards.get(highestCard);
	}

	/**
	 * @return the highest trump or null if there is no trump on the table.
	 */
	public Rank getHighestTrump() {
		Card highestTrumpCard = Card.highestTrump(this.trump,
				new ArrayList<Card>(this.cards.keySet()));

		if(highestTrumpCard != null) {
			return highestTrumpCard.getRank();
		}

		return null;
	}

	@Override
	public Trick clone() {
		Trick newTrick = new Trick(this.trump);
		newTrick.cards =  new HashMap<Card, Player>(this.cards);
		newTrick.leadingSuit = this.leadingSuit;
		return newTrick;
	}

}
