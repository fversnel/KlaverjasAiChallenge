/**
 * 
 */
package org.klaverjasaichallenge;

import org.klaverjasaichallenge.shared.Action;

/**
 * @author Joost
 *
 */
public interface Player {
	public Action respond(GameState gameState, RuleSet ruleset);
}
