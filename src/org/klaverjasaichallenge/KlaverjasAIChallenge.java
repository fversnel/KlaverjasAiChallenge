/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import nl.joostpastoor.joostklaverjasai.JoostAI;

import org.klaverjasaichallenge.GameState.GameStatePhases;
import org.klaverjasaichallenge.shared.AcceptTrumpAction;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.PlayCardAction;
import org.klaverjasaichallenge.shared.card.suit.Suit;

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

		while (state.getPhase() != GameStatePhases.FINISHED) {

			/**
			 * Phase: Choosing Trumps
			 * TODO Seems like this can be abstracted away from this
			 * high-level game loop. Perhaps use polymorphism to handle
			 * different game states.
			 */
			if (state.getPhase() == GameStatePhases.CHOOSING_TRUMP) {
				try {
					Suit trump = state.drawTrump();
					System.out.println("Trump drawn: " + trump);
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			Player currentPlayer = state.calculateCurrentPlayer();
			System.out.println("Current player: " + currentPlayer);

			Action playerAction = currentPlayer.respond(state, null);
			// TODO Apparently the last action is null, why is that? 
			// A bug I guess.
			if(playerAction != null) {
				playerAction.perform(state, currentPlayer);
			}
		}
	}
}
