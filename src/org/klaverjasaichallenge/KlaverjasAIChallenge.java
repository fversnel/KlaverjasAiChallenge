/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.ai.JoostAI;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.PlayCardAction;

/**
 * @author Joost
 * 
 */
public class KlaverjasAIChallenge {  

	public static void main(String[] args) {

		List<Player> players = new LinkedList<Player>();
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI()); 

		DefaultGameState state = new DefaultGameState(players);

		while (state.getPhase() != GameState.GameStatePhases.FINISHED) {
			for (final Player currentPlayer : players) {

				/**
				 * Request Action from player
				 */
				Action action = currentPlayer.respond(state, null);
				
				/**
				 * Alter gamestate with action
				 */
				if(action instanceof PlayCardAction) {
					
				}

			}
			
			// Hack to finish the game
			state.setPhase(GameState.GameStatePhases.FINISHED);
		}
	}
}
