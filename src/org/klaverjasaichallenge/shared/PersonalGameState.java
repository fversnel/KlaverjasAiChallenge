/**
 * 
 */
package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.GameState.GameStatePhases;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.card.Card;

/**
 * @author Joost
 * 
 */
public class PersonalGameState {
	private final List<Card> hand;
	private final Map<Player, Card> cardsOnTable;
	private final Suit trump;
	private final Player leadingPlayer;
	private final GameStatePhases phase;

	public PersonalGameState(List<Card> hand, Map<Player, Card> cardsOnTable, Suit trump, Player leadingPlayer,
			GameStatePhases phase) {
		this.hand = hand;
		this.cardsOnTable = cardsOnTable;
		this.trump = trump;
		this.leadingPlayer = leadingPlayer;
		this.phase = phase;
	}

	/**
	 * @return the hand
	 */
	public List<Card> getHand() {
		return hand;
	}

	/**
	 * @return the cardsOnTable
	 */
	public Map<Player, Card> getCardsOnTable() {
		return cardsOnTable;
	}

	/**
	 * @return the trump
	 */
	public Suit getTrump() {
		return trump;
	}

	/**
	 * @return the leadingPlayer
	 */
	public Player getLeadingPlayer() {
		return leadingPlayer;
	}

	/**
	 * @return the phase
	 */
	public GameStatePhases getPhase() {
		return phase;
	}
}
