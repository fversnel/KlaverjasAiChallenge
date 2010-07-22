/**
 * 
 */
package org.klaverjasaichallenge.shared;

import org.klaverjasaichallenge.DefaultGameState;

/**
 * @author Joost
 *
 */
public interface Action {

	public void perform(DefaultGameState gameState, Player currentPlayer);

}
