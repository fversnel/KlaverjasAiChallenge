/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.GameState.GameStatePhases;
import org.klaverjasaichallenge.ai.JoostAI;
import org.klaverjasaichallenge.shared.AcceptTrumpAction;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.PlayCardAction;
import org.klaverjasaichallenge.shared.Card.Suit;

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

			/**
			 * Phase: Choosing Trumps
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
			/**
			 * Request Action from player
			 */
			Action action = currentPlayer.respond(state, null);

			/**
			 * Alter gamestate with action
			 */
			if (action instanceof AcceptTrumpAction) {
				AcceptTrumpAction acceptTrumpAction = (AcceptTrumpAction) action;

				if (acceptTrumpAction.isTrumpAccepted())
					state.playerAcceptedTrump(currentPlayer);
			}

			if (action instanceof PlayCardAction) {
				PlayCardAction playCardAction = (PlayCardAction) action;
				try {
					state.playCard(playCardAction.getCard());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
