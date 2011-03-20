package org.klaverjasaichallenge.engine.round.action;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.CheatException;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class PlayRound extends RoundAction {
	private final Logger logger = Logger.getLogger(getClass());

	public PlayRound(final RoundData roundData) {
		super(roundData);
	}

	@Override
	public RoundAction execute() {
		Table table = this.roundData.getTable();
		for (int trickId = 1; trickId <= Trick.COUNT; trickId++) {
			EngineTrick trick = this.createNewTrick(trickId);

			this.playTrick(table, trick, trickId);
			this.roundData.addPlayedTrick(trick);
			this.notifyPlayersEndTrick(table, trick);

			final Player winner = trick.getWinner();
			table.nextTrick(winner);

			this.logger.debug("--- Winner:  " + winner + " with " +
					new Score(trick));
		}

		return new AccumulateTrickScore(this.roundData);
	}

	private EngineTrick createNewTrick(final int trickId) {
		final Suit trump = this.roundData.getTrump();
		final boolean isLastTrick = (trickId == Trick.COUNT);
		return new EngineTrick(trump, isLastTrick);
	}

	private void playTrick(Table table, final EngineTrick trick, final int trickId) {
		this.logger.debug("-- Starting trick " + trickId + " with trump " + trick.getTrump());

		for(final Player currentPlayer : table) {
			final Card cardPlayed = currentPlayer.getCard(trick);
			
			// Check if the card is valid
			try {
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
	private void playCard(final EngineTrick trick, final Player player,
			final Card card) throws CheatException {
		final Hand playersHand = this.roundData.getPlayersHand(player);
		final RuleSet ruleSet = this.roundData.getRuleSet();
		
		final boolean cardIsLegal = ruleSet.isLegalCard(trick.clone(), card, playersHand);
		final boolean cardIsInHand = playersHand.drawCard(card);
		if(cardIsLegal && cardIsInHand) {
			trick.addCard(player, card);
		} else {
			throw new CheatException("Player " + player + " cheated " +
					"with " + card + " and hand " + playersHand);
		}
	}

	private void notifyPlayersEndTrick(final Table table, final EngineTrick trick) {
		for (final Player player : table) {
			player.endOfTrick(trick.clone());
		}
	}

}
