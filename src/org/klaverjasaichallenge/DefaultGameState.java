/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;

/**
 * @author Joost
 * 
 */
public class DefaultGameState implements GameState {

	private final List<Player> players;
	private Card.Suit trump;
	private GameStatePhases phase;


	public DefaultGameState(List<Player> players) {
		this.players = players;
		this.setPhase(GameStatePhases.CHOOSING_TRUMP);
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
