/**
 * 
 */
package org.klaverjasaichallenge;

import org.klaverjasaichallenge.action.Action;

/**
 * @author Joost
 *
 */
public interface Player {
	public Action respond(GameState gameState, RuleSet ruleset);
}
