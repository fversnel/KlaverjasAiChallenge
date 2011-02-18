package org.klaverjasaichallenge.server.round.action;

import java.util.List;
import java.util.Collections;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.shared.Order;

/**
 * Drawing trump is the process of choosing which player plays and on which
 * trump.
 */
public class DrawTrump extends RoundAction {
	private static final int FORCED_PLAY_ON_TRUMP = 1;

	private final Logger logger = Logger.getLogger(DrawTrump.class);

	private final List<Suit> availableTrumps;
	private final Suit trumpDrawn;

	public DrawTrump(final RoundData roundData) {
		this(roundData, Card.getSuits());
	}

	public RoundAction execute() {
		int playerIndex = 0;
		for(Player player : this.roundData.getTable()) {
			// Start the round if the player wants to go on the trump.
			// Start the round anyway, forcing the current player to go on the
			// trump, when the third trump is drawn.
			if(player.playOnTrump(this.trumpDrawn, new Order(playerIndex)) ||
					this.availableTrumps.size() == FORCED_PLAY_ON_TRUMP) {
				this.roundData.setTrump(player, this.trumpDrawn);
				this.logger.debug(player + " goes on " +
						this.trumpDrawn);

				return new InformPlayersRoundStart(this.roundData);
			}

			playerIndex++;
		}

		return new DrawTrump(this.roundData, this.availableTrumps);
	}

	private DrawTrump(final RoundData roundData, final List<Suit> availableTrumps) {
		super(roundData);

		this.availableTrumps = availableTrumps;
		this.trumpDrawn = this.getRandomTrump();
	}

	private Suit getRandomTrump() {
		Collections.shuffle(this.availableTrumps);
		return this.availableTrumps.remove(0);
	}

}
