
package org.klaverjasaichallenge.engine.round;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Trick {
	public static final int COUNT = 8;

	private final static Points LAST_TRICK_POINTS = new Points(10);

	private static final int FIRST_ADDED_CARD = 1;
	private static final int TOTAL_CARDS = 4;

	private final Map<Card, Player> cards;
	private final Suit trump;

	private final boolean isLastTrick;

	private Suit leadingSuit;

	public Trick(final Suit trump, final boolean isLastTrick) {
		this.cards = new HashMap<Card, Player>();
		this.trump = trump;
		this.isLastTrick = isLastTrick;
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
		Score score = new Score(this);

		if(this.isLastTrick) {
			score = new Score(Points.plus(score.getStockScore(),
						LAST_TRICK_POINTS), score.getRoemScore());
		}

		return score;
	}

	/**
	 * @return null if there is no card played yet.
	 */
	public Player getWinner() {
		assert(this.cards.keySet().size() == TOTAL_CARDS) : "Cannot determine a winner " +
												   "if a trick is not fully played yet.";

		final Card highestCard = Card.max(this.trump, this.leadingSuit, new
				LinkedList<Card>(this.cards.keySet()));
		return this.cards.get(highestCard);
	}

	/**
	 * @return the highest trump or null if there is no trump on the table.
	 */
	public Card getHighestTrump() {
		return Card.highestTrump(this.trump,
				new LinkedList<Card>(this.cards.keySet()));
	}

	public Card getHighestCard() {
		return Card.max(this.trump, this.leadingSuit, new
				LinkedList<Card>(this.cards.keySet()));
	}

}
