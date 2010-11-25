/**
 *
 */
package org.klaverjasaichallenge.shared;

import java.util.List;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * @author Joost
 *
 */
public interface Player {

	/**
	 * The first thing in a game of klaverjas is determining the ruleset of a
	 * game. The AI can use this ruleSet to determine his legal cards for each
	 * trick.
	 */
	public void giveRuleSet(final RuleSet ruleSet);

	/**
	 * Give the player his 8 cards, this is the second thing that will happen in
	 * a game of Klaverjas.
	 * @param cards
	 */
	public void giveCards(List<Card> cards);

	/**
	 * Whether you play on the given trump or not.
	 * @param trump the suit that will be used as trump this round.
	 * @param order the number of your turn (order 0 means your the first to
	 * determine whether you play on the trump. Order 2 means there were 2
	 * players before that passed on the trump.)
	 * @return whether you play on the trump or not.
	 */
	public boolean playOnTrump(Suit trump, Order order);

	/**
	 * This method is used to inform the AI of the start of a new Round
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
	 * This is useful information since your AI will then know all cards
	 * that have been played in the most recently played trick.
	 *
	 * @param trick
	 */
	public void endOfTrick(Trick trick);

	@Override
	public String toString();
}
