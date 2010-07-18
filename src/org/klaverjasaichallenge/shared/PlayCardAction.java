/**
 * 
 */
package org.klaverjasaichallenge.shared;

import org.klaverjasaichallenge.shared.card.Card;


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
