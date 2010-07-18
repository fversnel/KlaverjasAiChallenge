/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.shared.Card;

/**
 * @author Joost
 * 
 */
public class DefaultGameState implements GameState {

	private final List<Player> players;
	private final Map<Player, List<Card>> hands;
	private Card.Suit trump;
	private GameStatePhases phase;


	public DefaultGameState(List<Player> players) {
		this.players = players;
		this.setPhase(GameStatePhases.CHOOSING_TRUMP);

		// TODO: Give the players their cards
		Deck deck = new Deck();
		this.hands = null;
	}


	/**
	 * @param phase the phase to set
	 */
	public void setPhase(GameStatePhases phase) {
		this.phase = phase;
	}


	/**
	 * @return the phase
	 */
	public GameStatePhases getPhase() {
		return phase;
	}	
}
