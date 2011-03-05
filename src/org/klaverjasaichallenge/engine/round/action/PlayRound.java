package org.klaverjasaichallenge.engine.round.action;

import java.util.List;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.CheatException;
import org.klaverjasaichallenge.engine.round.Hand;
import org.klaverjasaichallenge.engine.round.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class PlayRound extends RoundAction {
	private final Logger logger;

	public PlayRound(final RoundData roundData) {
		super(roundData);

		this.logger = Logger.getLogger(PlayRound.class);
	}

	@Override
	public RoundAction execute() {
		Table table = this.roundData.getTable();
		for (int trickId = 1; trickId <= Trick.COUNT; trickId++) {
			Trick trick = this.createNewTrick(trickId);

			this.playTrick(table, trick, trickId);
			this.roundData.addPlayedTrick(trick);
			this.notifyPlayersEndTrick(table, trick);

			final Player winner = trick.getWinner();
			table.nextTrick(winner);

			this.logger.debug("--- Winner:  " + winner + " with " +
					trick.getScore());
		}

		return new AccumulateTrickScore(this.roundData);
	}

	private Trick createNewTrick(final int trickId) {
		final Suit trump = this.roundData.getTrump();
		final boolean isLastTrick = (trickId == Trick.COUNT);
		return new Trick(trump, isLastTrick);
	}

	private void playTrick(Table table, final Trick trick, final int trickId) {
		this.logger.debug("-- Starting trick " + trickId + " with trump " + trick.getTrump());

		for(final Player currentPlayer : table) {
			// Ask the player to return a card
			final Card cardPlayed = currentPlayer.getCard(
					new org.klaverjasaichallenge.shared.Trick(trick));
			try {
				// Check if the card is valid
				this.playCard(trick, currentPlayer, cardPlayed);
			} catch (CheatException cheatException) {
				throw new RuntimeException(cheatException);
			}

			this.logger.debug("--- " + currentPlayer + " played " + cardPlayed);
		}
	}

	/**
	 * Checks if the card that a player returns is indeed
	 * a valid card to play within this trick.
	 *
	 * @throws CheatException thrown when the player cheats.
	 */
	private void playCard(final Trick trick, final Player player,
			final Card card) throws CheatException {
		final Hand playersHand = this.roundData.getPlayersHand(player);
		final List<Card> playersCards = playersHand.getCards();

		RuleSet ruleSet = this.roundData.getRuleSet();
		if(ruleSet.isLegalCard(new org.klaverjasaichallenge.shared.Trick(trick),
					card, playersCards)
				&& playersHand.drawCard(card)) {
			trick.addCard(player, card);
		} else {
			throw new CheatException("Player " + player + " cheated " +
					"with " + card + " and hand " + playersHand.getCards());
		}
	}

	private void notifyPlayersEndTrick(final Table table, final Trick trick) {
		for (final Player player : table) {
			player.endOfTrick(new org.klaverjasaichallenge.shared.Trick(trick));
		}
	}

}
