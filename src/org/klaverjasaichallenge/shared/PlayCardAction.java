/**
 * 
 */
package org.klaverjasaichallenge.shared;


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

}
