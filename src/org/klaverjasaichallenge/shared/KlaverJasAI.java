package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.card.Card;

/**
 * KlaverJasAI is the abstract class that AI's need to extend to
 * be able to act as AI player in the game.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 * @since 1.0
 */
public abstract class KlaverJasAI implements Player {
	
	/**
	 * This function serves as placeholder for AI's that are still using
	 * giveCards(List<Card> cards). Make sure to override this method
	 * before 1.5.
	 */
	@Override
	public void giveCards(Hand cards) {
		this.giveCards(cards.getCards());
	}
	
	/**
	 * Give the player his eight cards, this is the second action during a
	 * game of Klaverjas.
	 *
	 * @param cards the cards that are dealt to the player.
	 * @deprecated This will be removed in 1.5. Use giveCards(Hand cards) instead.
	 */
	@Deprecated 	
	public void giveCards(@SuppressWarnings("unused") List<Card> cards) {}
}
