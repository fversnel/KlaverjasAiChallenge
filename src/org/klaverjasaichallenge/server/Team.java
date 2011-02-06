/**
 *
 */
package org.klaverjasaichallenge.server;

import java.util.ArrayList;
import java.util.List;

import org.klaverjasaichallenge.shared.Player;

public class Team {
	private final Player playerOne;
	private final Player playerTwo;

	public Team(final Player playerOne, final Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public List<Player> getPlayers() {
		final List<Player> players = new ArrayList<Player>();
		players.add(this.playerOne);
		players.add(this.playerTwo);
		return players;
	}

	public Player getFirstPlayer() {
		return this.playerOne;
	}

	public Player getSecondPlayer() {
		return this.playerTwo;
	}

	public boolean hasPlayer(final Player player) {
		return player.equals(this.playerOne) || player.equals(this.playerTwo);
	}

	public Player getOtherPlayer(final Player player) {
		if(player.equals(this.playerOne)) {
			return this.playerTwo;
		}

		return this.playerOne;
	}

	@Override
	public String toString() {
		return "Team: (" + this.playerOne + " and " + this.playerTwo + ")";
	}

}
