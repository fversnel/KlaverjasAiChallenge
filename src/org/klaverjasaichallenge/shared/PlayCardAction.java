/**
 * 
 */
package org.klaverjasaichallenge.shared;


/**
 * Use this action to play a Card
 */
public class PlayCardAction implements Action {
	private Card card;

	public void setCard(Card card) {
		this.card = card;
	}

	public Card getCard() {
		return card;
	}

}
