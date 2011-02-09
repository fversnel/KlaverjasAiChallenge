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

class PlayRound extends RoundAction {
	// TODO This should be placed somewhere else, perhaps in the Player class.
	private final static int PLAYER_COUNT = 4;

	private final Logger logger;

	public PlayRound(final RoundData roundData) {
		super(roundData);

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	/**
	 * TODO Implement this method.
	 */
	@Override
	public RoundAction execute() {
		///**
		// * Action: Play all tricks, when the last trick is player, the round
		// * will be ended.
		// */
		final Suit trump = this.roundData.getTrump();
		Table table = this.roundData.getTable();
		for (int trickId = 1; trickId <= Trick.COUNT; trickId++) {
			final Trick trick = new Trick(trump);

			this.logger.debug("-- Starting trick " + trickId + " with trump " + trump);

			for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
				final Player currentPlayer = table.getActivePlayer();
				final Order playersOrder = new Order(playerIndex);

				// Ask the player to return a card
				final Card cardPlayed = currentPlayer.getCard(
						new org.klaverjasaichallenge.shared.Trick(trick), playersOrder);

				this.logger.debug("--- " + currentPlayer + " played " + cardPlayed);

				// Check if the card is valid
				try {
					this.playCard(trick, currentPlayer, cardPlayed);
				} catch (Exception e) {
					// TODO Do not print an error here, but punish!
					e.printStackTrace();
				}

				table = table.nextPlayer();
			}

			final Score score = trick.getScore();
			final Player winner = trick.getWinner();
			this.roundData.addPlayedTrick(trick);
			this.logger.debug("--- Winner:  " + winner + " with " + score);

			// Notify player of end of trick
			for (Player player : table.getPlayers()) {
				player.endOfTrick(new org.klaverjasaichallenge.shared.Trick(trick));
			}

			table = table.nextTrick(winner);
		}

		return new CalculateScore(this.roundData);
	}

	/**
	 * Playcard checks if the card that a player returns is indeed
	 * a valid card to play within this trick.
	 *
	 * TODO Implement this method.
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

		if(this.roundData.getRuleSet().isLegalCard(trick, card, playersHand.getCards())) {
			trick.addCard(player, card);
		} else {
			throw new CheatException("Player " + player + " cheated.");
		}
	}

}
