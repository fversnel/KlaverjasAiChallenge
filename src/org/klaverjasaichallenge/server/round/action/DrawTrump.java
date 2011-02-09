package org.klaverjasaichallenge.server.round.action;

import java.util.List;
import java.util.Random;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.Order;

class DrawTrump extends RoundAction {
	private static final int FORCED_PLAY_ON_TRUMP = 3;
	private static final int PLAYER_COUNT = 4;

	private final Logger logger;

	public DrawTrump(final RoundData roundData) {
		super(roundData);

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	/**
	 * Drawing trump is the process of choosing which player plays and on which
	 * trump.
	 *
	 * Drawing trump is the subprocedure of deciding who plays on which trump. At
	 * the end of this function the object variables this.trump and
	 * this.trumpPlayer will be set.
	 *
	 */
	@Override
	public RoundAction execute() {
		int amountTrumpsDrawn = 0;
		Suit trumpDrawn = null;
		Player trumpPlayer = null;
		final List<Suit> availableTrumps = Card.getSuits();
		do {
			trumpDrawn = this.getRandomTrump(availableTrumps);
			availableTrumps.remove(trumpDrawn);

			amountTrumpsDrawn++;

			Table trumpTable = this.roundData.getTable();
			for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
				final Player player = trumpTable.getActivePlayer();

				// When the player decides he wants to play on this trump
				// After three tries force active player to go on this third trump
				if (player.playOnTrump(trumpDrawn, new Order(playerIndex)) ||
						amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
					trumpPlayer = player;

					if(amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
						this.logger.debug(trumpPlayer + " is " +
								"forced to go on" + trumpDrawn);
					} else {
						this.logger.debug(trumpPlayer + " goes on " +
								trumpDrawn);
					}

					break;
				}

				trumpTable = trumpTable.nextPlayer();
			}

		} while (trumpPlayer == null);

		// Asserts that this function always results in a filled
		// trumpPlayer and trump
		assert (trumpDrawn != null) : "There is no trump drawn.";
		assert (trumpPlayer != null) : "None of the players has " +
				"accepted the trump.";

		this.roundData.setTrump(trumpDrawn);
		this.roundData.setTrumpPlayer(trumpPlayer);

		return new InformPlayersRoundStart(this.roundData);
	}

	private Suit getRandomTrump(final List<Suit> availableTrumps) {
		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(availableTrumps.size());
		return availableTrumps.get(trumpIndex);
	}
}
