
package org.klaverjasaichallenge.engine.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.EnumMap;
import java.util.NoSuchElementException;
import com.google.common.base.Optional;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class EngineTrick implements Trick {
	private static final int FIRST_ADDED_CARD = 1;
	private static final int TOTAL_CARDS = 4;

	private Map<Card, Player> cards = new EnumMap<Card, Player>(Card.class);

	private Optional<Suit> leadingSuit = Optional.absent();
	private final Suit trump;

	private final boolean isLastTrick;

	public EngineTrick(final Suit trump, final boolean isLastTrick) {
		this.trump = trump;
		this.isLastTrick = isLastTrick;
	}

	public void addCard(final Player player, final Card cardPlayed) {
		this.cards.put(cardPlayed, player);

		if(this.cards.size() == FIRST_ADDED_CARD) {
			this.leadingSuit = Optional.of(cardPlayed.getSuit());
		}

		assert(this.leadingSuit.isPresent()) : "The leading suit was not properly determined.";
		assert(this.numberOfCards() <= TOTAL_CARDS) : "Trick cannot contain more than " + TOTAL_CARDS + ".";
	}

	@Override
	public List<Card> getCards() {
		return new ArrayList<Card>(this.cards.keySet());
	}

	@Override
	public int numberOfCards() {
		return this.cards.size();
	}

	/**
	 * This function transforms getCards (who has Player objects as value)
	 * to a getCards with PlayerID's as value, so its safe to pass them to
	 * clients.
	 *
	 * @return Cards mapped to player ID's
	 */
	@Override
	public Map<Card, Integer> getCardsWithPlayers() {
		return Maps.transformValues(this.cards, new Function<Player, Integer>() {
			public Integer apply(final Player player) {
				return player.hashCode();
			}
		});
	}

	@Override
	public Card getCardFromPlayer(final int playerId) {
		try {
			return Iterables.find(this.cards.keySet(), new Predicate<Card>() {
				public boolean apply(final Card card) {
					return cards.get(card).hashCode() == playerId;
				}
			});
		} catch(NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Suit getTrump() {
		return this.trump;
	}

	@Override
	public Suit getLeadingSuit() {
		return this.leadingSuit.orNull();
	}

	@Override
	public boolean isLastTrick() {
		return this.isLastTrick;
	}

	public Player getWinner() {
		assert(this.cards.keySet().size() == TOTAL_CARDS) : "Cannot determine a winner " +
				"if a trick is not fully played yet.";

		return this.cards.get(this.getHighestCard());
	}

	/**
	 * @return the highest trump or null if there is no trump on the table.
	 */
	@Override
	public Card getHighestTrump() {
		return Card.highestTrump(this.trump,
				new ArrayList<Card>(this.cards.keySet()));
	}

	@Override
	public Card getHighestCard() {
		return Card.max(this, this.getCards());
	}

	@Override
	public Trick clone() {
		final EngineTrick newTrick = new EngineTrick(this.trump, this.isLastTrick);
		newTrick.cards = new EnumMap<Card, Player>(this.cards);
		newTrick.leadingSuit = this.leadingSuit;
		return newTrick;
	}

	@Override
	public String toString() {
		String result = "";
		for(final Card card : this.cards.keySet()) {
			final Player player = this.cards.get(card);
			result += String.format("\n%s played %s", player, card);
		}
		result += "\n";
		return result;
	}

}
