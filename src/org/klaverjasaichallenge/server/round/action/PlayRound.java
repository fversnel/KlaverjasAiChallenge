package org.klaverjasaichallenge.server.round.action;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.server.round.Trick;
import org.klaverjasaichallenge.server.round.CheatException;
import org.klaverjasaichallenge.server.round.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.score.Score;
import org.klaverjasaichallenge.shared.RuleSet;

class PlayRound extends RoundAction {
	// TODO This should be placed somewhere else, perhaps in the Player class.
	private final static int PLAYER_COUNT = 4;

	private final Logger logger;

	public PlayRound(final RoundData roundData) {
		super(roundData);

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	@Override
	public RoundAction execute() {
		this.playTricks();

		return new CalculateScore(this.roundData);
	}

	private void playTricks() {
		Table table = this.roundData.getTable();
		for (int trickId = 1; trickId <= Trick.COUNT; trickId++) {
			final Suit trump = this.roundData.getTrump();
			final Trick trick = new Trick(trump);

			this.playTrick(table, trick, trickId);
			this.roundData.addPlayedTrick(trick);
			this.notifyPlayersEndTrick(table, trick);

			final Score score = trick.getScore();
			final Player winner = trick.getWinner();
			this.logger.debug("--- Winner:  " + winner + " with " + score);
			table = table.nextTrick(winner);
		}
	}

	private void playTrick(Table table, final Trick trick, final int trickId) {
		this.logger.debug("-- Starting trick " + trickId + " with trump " + trick.getTrump());

		for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
			final Player currentPlayer = table.getActivePlayer();
			final Order playersOrder = new Order(playerIndex);

			// Ask the player to return a card
			final Card cardPlayed = currentPlayer.getCard(
					new org.klaverjasaichallenge.shared.Trick(trick), playersOrder);
			// Check if the card is valid
			try {
				this.playCard(trick, currentPlayer, cardPlayed);
			} catch (CheatException cheatException) {
				// TODO Do not print an error here, but punish!
				this.logger.info(cheatException.getMessage());
			}

			this.logger.debug("--- " + currentPlayer + " played " + cardPlayed);

			table = table.nextPlayer();
		}
	}

	/**
	 * Playcard checks if the card that a player returns is indeed
	 * a valid card to play within this trick.
	 *
	 * @param trick
	 * @param player
	 * @param card
	 * @throws Exception
	 */
	private void playCard(final Trick trick, final Player player,
			final Card card) throws CheatException {
		Hand playersHand = this.roundData.getPlayersHand(player);
		if (playersHand.drawCard(card) == null) {
			throw new CheatException("Player " + player + " played an invalid " +
					" card. The card (" + card + ") is not in his hand.");
		}

		RuleSet ruleSet = this.roundData.getRuleSet();
		if(ruleSet.isLegalCard(trick, card, playersHand.getCards())) {
			trick.addCard(player, card);
		} else {
			throw new CheatException("Player " + player + " cheated.");
		}
	}

	private void notifyPlayersEndTrick(final Table table, final Trick trick) {
		for (Player player : table.getPlayers()) {
			player.endOfTrick(new org.klaverjasaichallenge.shared.Trick(trick));
		}
	}

}
