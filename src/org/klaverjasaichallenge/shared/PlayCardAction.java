/**
 * 
 */
package org.klaverjasaichallenge.shared;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.DefaultGameState;

/**
 * Use this action to play a Card
 */
public class PlayCardAction implements Action {
	private final Card card;

	public PlayCardAction(Card card) {
		this.card = card;
	}

	public Card getCard() {
		return card;
	}

	public void perform(DefaultGameState gameState, Player currentPlayer) {
		try {
			gameState.playCard(this.getCard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "PlayCardAction - " + this.card;
	}	

}
