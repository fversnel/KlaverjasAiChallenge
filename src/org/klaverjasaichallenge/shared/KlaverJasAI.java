package org.klaverjasaichallenge.shared;

/**
 * 
 */

/**
 * KlaverJasAI is the interface that classes need to implement to
 * be able to act as AI player in the game.
 * 
 * @author Joost Pastoor <info@joostpastoor.nl>
 * @version 1.0
 */
public interface KlaverJasAI extends Player {

	// TODO Use this to construct the interface for the game.
	// These are just some ideas for an interface that is provided to the
	// AI by the game:
	//public Trick getLastPlayedTrick();
	//public void playCard(Card card);
	//public Points calculateRoemScore(Trick trick)
	//public Points calculateStockScore(Trick trick)
	//public Trick getCardsOnTable()
	//public Suit getTrump()
}
