
package org.klaverjasaichallenge.engine.round;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.EnumMap;
import java.util.Map.Entry;

import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
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
		this.cards = new EnumMap<Card, Player>(Card.class);
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

	/**
	 * @deprecated use {@link Trick#getCardFromPlayer} instead
	 */
	@Deprecated
	public Map<Card, Player> getCardsWithPlayers() {
		return this.cards;
	}

	/**
	 * This function transforms getCards (who has Player objects as value)
	 * to a getCards with PlayerID's as value, so its safe to pass them to
	 * clients.
	 *
	 * @deprecated use {@link Trick#getCardFromPlayer} instead
	 * @return Cards mapped to player ID's
	 */
	@Deprecated
	public Map<Card, Integer> getCardsWithPlayersIds() {
		Map<Card, Integer> cardsWithPlayersIds = new HashMap<Card, Integer>();
		for(Entry<Card, Player> cardWithPlayer : this.cards.entrySet())
			cardsWithPlayersIds.put(cardWithPlayer.getKey(), cardWithPlayer.getValue().hashCode()) ;
		return cardsWithPlayersIds;
	}

	public Card getCardFromPlayer(final int playerId) {
		Card playerCard = null;

		for(final Card card : this.cards.keySet()) {
			Player player = this.cards.get(card);

			if(player != null && player.hashCode() == playerId) {
				playerCard = card;
			}
		}

		return playerCard;
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

		return this.cards.get(this.getHighestCard());
	}

	/**
	 * @return the highest trump or null if there is no trump on the table.
	 */
	public Card getHighestTrump() {
		return Card.highestTrump(this.trump,
				new LinkedList<Card>(this.cards.keySet()));
	}

	public Card getHighestCard() {
		return Card.max(Trick.convertToSharedTrick(this), this.getCards());
	}

	private static org.klaverjasaichallenge.shared.Trick convertToSharedTrick(final Trick trick) {
		return new org.klaverjasaichallenge.shared.Trick(trick);
	}

}
