/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import nl.joostpastoor.joostklaverjasai.JoostAI;

import org.klaverjasaichallenge.shared.PersonalGameState.GameStatePhases;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

/**
 * @author Joost
 * 
 */
public class KlaverjasAIChallenge {
	
	private final static int PLAY_GAMES = 10;

	public static void main(String[] args) {

		List<Player> players = new LinkedList<Player>();
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI());
		
		for(int currentGameId = 0; currentGameId < PLAY_GAMES; currentGameId++) {
	
			DefaultGameState game = new DefaultGameState(players);
			game.play();
			game.getScores();

		}
		
//		
//
//		while (state.getPhase() != GameStatePhases.FINISHED) {
//
//			/**
//			 * Phase: Choosing Trumps
//			 * TODO Seems like this can be abstracted away from this
//			 * high-level game loop. Perhaps use polymorphism to handle
//			 * different game states.
//			 */
//			if (state.getPhase() == GameStatePhases.CHOOSING_TRUMP) {
//				try {
//					Suit trump = state.drawTrump();
//					System.out.println("Trump drawn: " + trump);
//				} catch (Exception e) {
//					System.out.println(e);
//				}
//			}
//
//			Player currentPlayer = state.calculateCurrentPlayer();
//
//			Action playerAction = currentPlayer.respond(state.getPersonalGameState(currentPlayer));
//
//			System.out.println("Current player: " + currentPlayer + " action: " + playerAction);
//			
//			// TODO Apparently the last action is null, why is that?
//			// A bug I guess.
//			if(playerAction != null) {
//				playerAction.perform(state, currentPlayer);
//			}
//		}
	}
}
