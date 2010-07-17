package org.klaverjasaichallenge;
/**
 * 
 */

/**
 * @author Joost
 *
 */
public interface GameState {

	public enum GameStatePhases {
		CHOOSING_TRUMP, PLAYING, FINISHED
	}
	
	public GameStatePhases getPhase();
	
}
