package org.klaverjasaichallenge.server.round.action;

import java.util.List;
import java.util.Random;

import org.klaverjasaichallenge.server.Table;
import org.klaverjasaichallenge.server.round.RoundResult;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

class DrawTrump extends RoundAction {

	public DrawTrump(final RoundData roundData) {
		super(roundData);
	}

	/**
	 * Drawing trump is the process of choosing which player plays and on which
	 * trump.
	 *
	 * Drawing trump is the subprocedure of deciding who plays on which trump. At
	 * the end of this function the object variables this.trump and
	 * this.trumpPlayer will be set.
	 *
	 * TODO See if we can make this.trump and trumpPlayer local
	 * variables that only exist in play() and subprocedures of play. Maybe let
	 * this function return an object that contains them both.
	 */
	@Override
	public RoundAction execute() {
		//Suit trumpDrawn = null;
		//int amountTrumpsDrawn = 0;
		//boolean trumpPlayer = false;
		//final List<Suit> availableTrumps = Card.getSuits();
		//do {
		//	trumpDrawn = this.getRandomTrump(availableTrumps);
		//	availableTrumps.remove(trumpDrawn);

		//	amountTrumpsDrawn++;

		//	Table trumpTable = this.table;
		//	for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
		//		final Player player = trumpTable.getActivePlayer();

		//		// When the player decides he wants to play on this trump
		//		// After three tries force active player to go on this third trump
		//		if (player.playOnTrump(trumpDrawn, new Order(playerIndex)) ||
		//				amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
		//			trumpPlayer = player;

		//			if(amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
		//				this.logger.debug(this.trumpPlayer + " is " +
		//						"forced to go on" + trumpDrawn);
		//			} else {
		//				this.logger.debug(this.trumpPlayer + " goes on " +
		//						trumpDrawn);
		//			}

		//			break;
		//		}

		//		trumpTable = trumpTable.nextPlayer();
		//	}

		//} while (trumpPlayer == null);

		//// Asserts that this function always results in a filled
		//// trumpPlayer and trump
		//assert (trumpPlayer != null) : "None of the players has " +
		//		"accepted the trump.";
		//assert (trumpDrawn != null) : "There is no trump drawn.";

		// TODO Execute the next Action.
		// Create a new Action with (this.table, this.ruleSet, trump,
		// trumpPlayer)
		return null;
	}

	private Suit getRandomTrump(final List<Suit> availableTrumps) {
		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(availableTrumps.size());
		return availableTrumps.get(trumpIndex);
	}
}
