package org.klaverjasaichallenge.shared;

import java.util.List;
import java.util.Map;
import com.google.common.base.Optional;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/*
 * The {@link Trick} is one of the main concepts in a round of Klaverjas.
 * Each round consists of eight tricks, each trick contains four {@link
 * Card}'s.  In turns, each {@link org.klaverjasaichallenge.shared.Player}
 * plays a card in the trick, after which a winner is determined. The winner
 * gets all points of the cards in the trick and is also the first to play a
 * card in the next trick (unless its the last trick in a round).
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public interface Trick {
	public final static int COUNT = 8;

	/**
	 * @return the cards that are currently played.
	 */
	public List<Card> getCards();

	/**
	 *
	 * @return the trump of this trick
	 */
	public Suit getTrump();

	/**
	 * All players have to follow the leading suit if they can.
	 *
	 * @return the suit of the first card, or null if no cards are played yet.
	 */
	public Suit getLeadingSuit();

	/**
	 * @return a map with the cards that are played by their associated
	 * player ID's.
	 */
	public Map<Card, Integer> getCardsWithPlayers();

	/**
	 * @return the card of the requested player or null if he didn't play
	 * anything yet.
	 */
	public Card getCardFromPlayer(final int playerId);

	/**
	 *
	 * @return the highest trump, or null if no trump is played.
	 */
	public Card getHighestTrump();

	/**
	 *
	 * @return the highest card that is played.
	 */
	public Card getHighestCard();

	/**
	 *
	 * @return the number of cards played.
	 */
	public int numberOfCards();

	/**
	 *
	 * @return true if this is the last (eight) trick, false if it isn't.
	 */
	public boolean isLastTrick();

}
