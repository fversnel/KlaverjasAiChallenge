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

public class DrawTrump extends RoundAction {
	private static final int FORCED_PLAY_ON_TRUMP = 1;

	private final Logger logger;

	private final List<Suit> availableTrumps;

	public DrawTrump(final RoundData roundData) {
		super(roundData);

		this.availableTrumps = Card.getSuits();

		this.logger = Logger.getLogger(DrawTrump.class);
	}

	/**
	 * Drawing trump is the process of choosing which player plays and on which
	 * trump.
	 */
	@Override
	public RoundAction execute() {
		Suit trumpDrawn = null;
		Player trumpPlayer = null;
		while(trumpPlayer == null) {
			trumpDrawn = this.getRandomTrump();

			Table table = this.roundData.getTable();
			int playerIndex = 0;
			for(Player player : this.roundData.getTable()) {
				// When the player decides he wants to play on this trump
				// After three tries force active player to go on this third trump
				if(player.playOnTrump(trumpDrawn, new Order(playerIndex))
					|| this.availableTrumps.size() == FORCED_PLAY_ON_TRUMP) {
					trumpPlayer = player;

					this.logger.debug(trumpPlayer + " goes on " +
							trumpDrawn);

					break;
				}

				playerIndex++;
			}
		}

		// Asserts that this function always results in a filled
		// trumpPlayer and trump
		assert (trumpDrawn != null) : "There is no trump drawn.";
		this.roundData.setTrump(trumpDrawn);
		assert (trumpPlayer != null) : "None of the players has " +
				"accepted the trump.";
		this.roundData.setTrumpPlayer(trumpPlayer);

		return new InformPlayersRoundStart(this.roundData);
	}

	private Suit getRandomTrump() {
		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(this.availableTrumps.size());
		final Suit drawnTrump = this.availableTrumps.get(trumpIndex);
		this.availableTrumps.remove(drawnTrump);

		return drawnTrump;
	}

}
