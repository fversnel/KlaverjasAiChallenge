package org.klaverjasaichallenge.engine.round.action;

import java.util.Collections;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 * Drawing trump is the process of choosing which player plays and on which
 * trump.
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class DrawTrump implements RoundAction<TrumpPlayer> {
	private static final Logger logger = LoggerFactory.getLogger(DrawTrump.class);
	
	private static final int MINIMUM_AVAILABLE_TRUMPS_LEFT = 1;

	private final Table table;

	private final Stack<Suit> availableTrumps;

	public DrawTrump(final Table table) {
		this.table = table;
		
		this.availableTrumps = new Stack<Suit>();
		this.availableTrumps.addAll(Suit.asList());
		Collections.shuffle(this.availableTrumps);
	}

	@Override
	public TrumpPlayer execute() {
		assert(this.availableTrumps.size() >= MINIMUM_AVAILABLE_TRUMPS_LEFT);
		
		final Suit trumpDrawn = this.availableTrumps.pop();
		
		int playerIndex = 0;
		for(Player player : this.table) {
			
			final boolean voluntaryPlay = player.playsOnTrump(trumpDrawn, playerIndex);
			final boolean forcedPlay = this.availableTrumps.size() == MINIMUM_AVAILABLE_TRUMPS_LEFT;
			if(voluntaryPlay || forcedPlay) {
				logger.debug(player + " plays on trump " + trumpDrawn);
				return new TrumpPlayer(player, trumpDrawn);
			}

			playerIndex++;
		}

		return execute();
	}

}
