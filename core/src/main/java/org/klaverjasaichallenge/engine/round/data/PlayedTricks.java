package org.klaverjasaichallenge.engine.round.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Trick;

/**
 * 
 * @author Frank Versnel
 */
public class PlayedTricks {
	private final Map<Trick, Player> playedTricks = new HashMap<Trick, Player>();

	public void addPlayedTrick(final EngineTrick playedTrick) {
		this.playedTricks.put(playedTrick, playedTrick.getWinner());
	}

	public List<Trick> getTricksPlayed() {
		return new ArrayList<Trick>(this.playedTricks.keySet());
	}

	public Player getWinner(final Trick playedTrick) {
		return this.playedTricks.get(playedTrick);
	}

}
