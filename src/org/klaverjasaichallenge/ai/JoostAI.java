/**
 * 
 */
package org.klaverjasaichallenge.ai;

import java.util.List;

import org.klaverjasaichallenge.GameState;
import org.klaverjasaichallenge.KlaverJasAI;
import org.klaverjasaichallenge.RuleSet;
import org.klaverjasaichallenge.GameState.GameStatePhases;
import org.klaverjasaichallenge.shared.AcceptTrumpAction;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.Card;
import org.klaverjasaichallenge.shared.PlayCardAction;

/**
 * @author Joost Pastoor
 * 
 * Joosts implementation of the KlaverJas AI
 *
 */
public class JoostAI implements KlaverJasAI {

	public Action respond(GameState gameState, RuleSet ruleset) {

		/**
		 * Respond on AcceptTrump
		 */
		if(gameState.getPhase() == GameStatePhases.CHOOSING_TRUMP) {
			return new AcceptTrumpAction(true);
		}

		if(gameState.getPhase() == GameStatePhases.PLAYING) {
			List<Card> hand = gameState.getPlayerHand(this);
			return new PlayCardAction(hand.get(0)); 
		}
		
		return null;
	}
}
