/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public interface Player {

	/**
	 * The first thing in a game of Klaverjas is informing which ruleset is
	 * used (Rotterdams, Amsterdams). The AI can use this ruleSet to determine
	 * his legal cards for each trick.
	 */
	public void giveRuleSet(final RuleSet ruleSet);

	/**
	 * Give the player his eight cards, this is the second action during a
	 * game of Klaverjas.
	 *
	 * @param cards the cards that are dealt to the player.
	 */
	public void giveCards(List<Card> cards);

	/**
	 * Whether you play on the given trump or not.
	 *
	 * @param trump the suit that will be used as trump this round.
	 * @param order the number of your turn (order 0 means your the first to
	 * determine whether you play on the trump. Order 2 means there were 2
	 * players before that passed on the trump.)
	 * @return whether you play on the trump or not.
	 */
	public boolean playOnTrump(Suit trump, Order order);

	/**
	 * Informs about the fact that a round is going to start.
	 *
	 * @param leadingPlayer The ID of the player that commits to the game
	 * @param trump The suit that is used as trump this round.
	 * @param yourId The ID you have in this round
	 * @param teamMateId The ID of your teammate
	 * @param enemy1Id The ID of one of your enemies
	 * @param enemy2Id The ID of the other enemy
	 */
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1Id, int enemy2Id);

	/**
	 * Play a card on the table.
	 *
	 * @param trick the trick that is currently being played. This variable
	 * can be used to request all cards that were already played in this
	 * trick.
	 * @param order the number of your turn (or: how many players were before
	 * you.)
	 * @return the card that you will play this round.
	 */
	public Card getCard(Trick trick, Order order);

	/**
	 * Returns the trick when it has been played, so the players
	 * know which cards have left the game.
	 * This is useful information since your the player will then know all cards
	 * that have been played in the most recently played trick.
	 *
	 * @param trick
	 */
	public void endOfTrick(Trick trick);

	@Override
	public String toString();

}
