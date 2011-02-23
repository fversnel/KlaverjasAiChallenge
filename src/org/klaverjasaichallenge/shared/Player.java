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
	 * The first thing in a game of Klaverjas is informing which {@link
	 * RuleSet} is used (Rotterdams, Amsterdams). The AI can use this ruleset
	 * to determine his legal cards for each trick.
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
	 * @param trump the {@link Suit} that will be used as trump this round.
	 * @param order the number of your turn ({@link Order} 0 means your the
	 * first to determine whether you play on the trump. {@link Order} 2 means
	 * there were two {@link Player}'s before that passed on the trump.)
	 *
	 * @return whether you play on the trump or not.
	 */
	public boolean playOnTrump(Suit trump, Order order);

	/**
	 * Informs about the fact that a round is going to start.
	 *
	 * @param leadingPlayer the ID of the player that commits to the game.
	 * @param trump the {@link Suit} that is used as trump this round.
	 * @param yourId the ID you have in this round.
	 * @param teamMateId the ID of your teammate.
	 * @param enemy1Id the ID of one of your enemies.
	 * @param enemy2Id the ID of the other enemy.
	 */
	public void startOfRound(int leadingPlayer, Suit trump, int yourId,
			int teamMateId, int enemy1Id, int enemy2Id);

	/**
	 * Play a {@link Card} on the table.
	 *
	 * @param trick the {@link Trick} that is currently being played. This variable
	 * can be used to request all cards that were already played in this
	 * trick.
	 * @param order the number of your turn (or: how many players were before
	 * you.)
	 * @return the {@link Card} that you will play this round.
	 */
	public Card getCard(Trick trick, Order order);

	/**
	 * Returns the {@link Trick} when it has been played, so the players know
	 * which cards have left the game.  This is useful information since the
	 * player will know all cards that have been played in the recently played
	 * trick.
	 *
	 * @param trick the {@link Trick} that has just been played.
	 */
	public void endOfTrick(Trick trick);

	/**
	 * Give yourself a nice name.
	 */
	@Override
	public String toString();

}
