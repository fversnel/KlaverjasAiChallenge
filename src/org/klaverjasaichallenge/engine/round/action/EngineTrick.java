
package org.klaverjasaichallenge.engine.round.action;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.EnumMap;
import java.util.Map.Entry;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
class EngineTrick implements Trick {
	private static final int FIRST_ADDED_CARD = 1;
	private static final int TOTAL_CARDS = 4;

	private Map<Card, Player> cards = new EnumMap<Card, Player>(Card.class);

	private Suit leadingSuit;
	private final Suit trump;

	private final boolean isLastTrick;

	public EngineTrick(final Suit trump, final boolean isLastTrick) {
		this.trump = trump;
		this.isLastTrick = isLastTrick;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(cardPlayed, player);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = cardPlayed.getSuit();
		}

		assert(this.leadingSuit != null) : "The leading suit was not properly determined.";
		assert(this.numberOfCards() <= TOTAL_CARDS) : "Trick cannot contain more than " + TOTAL_CARDS + ".";
	}

	@Override
	public List<Card> getCards() {
		return new LinkedList<Card>(this.cards.keySet());
	}

	@Override
	public int numberOfCards() {
		return this.cards.size();
	}

	/**
	 * This function transforms getCards (who has Player objects as value)
	 * to a getCards with PlayerID's as value, so its safe to pass them to
	 * clients.
	 *
	 * @return Cards mapped to player ID's
	 */
	@Override
	public Map<Card, Integer> getCardsWithPlayers() {
		Map<Card, Integer> cardsWithPlayersIds = new HashMap<Card, Integer>();
		for(Entry<Card, Player> cardWithPlayer : this.cards.entrySet())
			cardsWithPlayersIds.put(cardWithPlayer.getKey(), cardWithPlayer.getValue().hashCode()) ;
		return cardsWithPlayersIds;
	}

	@Override
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

	@Override
	public Suit getTrump() {
		return this.trump;
	}

	/**
	 * @return null if the trick has no cards.
	 */
	@Override
	public Suit getLeadingSuit() {
		return this.leadingSuit;
	}

	@Override
	public boolean isLastTrick() {
		return this.isLastTrick;
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
	@Override
	public Card getHighestTrump() {
		return Card.highestTrump(this.trump,
				new LinkedList<Card>(this.cards.keySet()));
	}

	@Override
	public Card getHighestCard() {
		return Card.max(this, this.getCards());
	}
	
	@Override
	public Trick clone() {
		final EngineTrick newTrick = new EngineTrick(this.trump, this.isLastTrick);
		newTrick.cards = this.cards;
		newTrick.leadingSuit = this.leadingSuit;
		return newTrick;
	}

}
