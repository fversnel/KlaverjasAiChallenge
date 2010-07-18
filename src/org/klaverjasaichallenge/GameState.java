package org.klaverjasaichallenge;

import java.util.List;

import org.klaverjasaichallenge.shared.card.Card;
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
	public List<Card> getPlayerHand(Player player);
	
}
