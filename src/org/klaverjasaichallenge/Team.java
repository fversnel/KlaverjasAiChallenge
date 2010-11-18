/**
 *
 */
package org.klaverjasaichallenge;

import java.util.ArrayList;
import java.util.List;

import org.klaverjasaichallenge.shared.Player;

public class Team {
	private Player playerOne;
	private Player playerTwo;

	public Team(final Player playerOne, final Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
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
		boolean result = false;
		if(player.equals(this.playerOne) || player.equals(this.playerTwo)) {
			result = true;
		}
		return result;
	}

	public Player getOtherPlayer(final Player player) {
		Player otherPlayer = null;

		if(player.equals(this.playerOne)) {
			otherPlayer = this.playerTwo;
		} else if(player.equals(this.playerTwo)) {
			otherPlayer = this.playerOne;
		}

		assert(otherPlayer != null);

		return otherPlayer;
	}

	@Override
	public String toString() {
		return "Team: (" + this.playerOne + " and " + this.playerTwo + ")";
	}

}
