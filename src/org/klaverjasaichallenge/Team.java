/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Player;

public class Team {
	private List<Player> players;

	public Team(final Player playerOne, final Player playerTwo) {
		this.players = new LinkedList<Player>();
		this.players.add(playerOne);
		this.players.add(playerTwo);
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getFirstPlayer() {
		return this.players.get(0);
	}

	public Player getSecondPlayer() {
		return this.players.get(1);
	}

	public boolean hasPlayer(final Player player) {
		return this.players.contains(player);
	}

}
