/**
 *
 */
package org.klaverjasaichallenge.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.klaverjasaichallenge.shared.Player;

public class Table implements Iterable<Player> {
	private final List<Team> teams;
	private final List<Player> playerPositions;

	private Player roundStarter;
	private Player trickStarter;

	public Table(final Team teamOne, final Team teamTwo) {
		this.teams = this.initializeTeams(teamOne, teamTwo);
		this.playerPositions = this.initializePlayerPositions(teamOne, teamTwo);

		this.roundStarter = teamOne.getFirstPlayer();
		this.trickStarter = this.roundStarter;
	}

	@Override
	public Iterator<Player> iterator() {
		return new PlayerIterator(this.trickStarter);
	}

	/**
	 * In the next trick, the winner of the previous trick is allowed to
	 * start.
	 */
	public void nextTrick(final Player winner) {
		this.trickStarter = winner;
	}

	public void nextRound() {
		this.roundStarter = new PlayerIterator(this.roundStarter).
				getNextPlayer(this.roundStarter);
		this.trickStarter = this.roundStarter;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public Team getTeamFromPlayer(final Player player) {
		return this.getTeam(player, false);
	}

	public Team getOtherTeam(final Team team) {
		Team result = null;

		for(final Team otherTeam : this.teams) {
			if(!team.equals(otherTeam)) {
				result = otherTeam;
			}
		}

		assert(result != null) : "Could not find other team.";

		return result;
	}

	public Team getOtherTeam(final Player player) {
		return this.getTeam(player, true);
	}

	@Override
	public String toString() {
		String newString = new String();
		for(Player player : this) {
			newString = newString + player + "\n";
		}
		return newString;
	}

	/**
	 * @param player the team the player is in.
	 * @param otherTeam indicates whether the opposite team (which the player
	 * is not in) will be returned.
	 */
	private Team getTeam(final Player player, final boolean otherTeam) {
		Team selectedTeam = null;

		for(Team team : this.teams) {
			if(team.hasPlayer(player) && !otherTeam) {
				selectedTeam = team;
			} else if (!team.hasPlayer(player) && otherTeam) {
				selectedTeam = team;
			}
		}

		assert(selectedTeam != null);

		return selectedTeam;
	}

	private List<Team> initializeTeams(final Team teamOne, final Team teamTwo) {
		final List<Team> teams = new ArrayList<Team>();
		teams.add(teamOne);
		teams.add(teamTwo);
		return teams;
	}

	private List<Player> initializePlayerPositions(final Team teamOne, final Team teamTwo) {
		final List<Player> positions =  new LinkedList<Player>();
		positions.add(teamOne.getFirstPlayer());
		positions.add(teamTwo.getFirstPlayer());
		positions.add(teamOne.getSecondPlayer());
		positions.add(teamTwo.getSecondPlayer());
		return positions;
	}

	private class PlayerIterator implements Iterator<Player> {
		private final int playerCount;
		private int playerIteration;

		private Player activePlayer;

		public PlayerIterator(final Player activePlayer) {
			this.activePlayer = activePlayer;

			this.playerCount = Table.this.playerPositions.size();
			this.playerIteration = 0;
		}

		@Override
		public Player next() {
			if(this.playerIteration == this.playerCount) {
				throw new NoSuchElementException();
			}

			final Player currentPlayer = this.activePlayer;
			this.activePlayer = this.getNextPlayer(this.activePlayer);
			this.playerIteration++;

			return currentPlayer;
		}

		@Override
		public boolean hasNext() {
			return this.playerIteration < this.playerCount;
		}

		@Override
		public void remove() {
			Table.this.playerPositions.remove(this.activePlayer);
		}

		public Player getNextPlayer(final Player currentPlayer) {
			final int currentPlayerIndex = Table.this.playerPositions.indexOf(currentPlayer);
			final int nextPlayerIndex = (currentPlayerIndex + 1) % this.playerCount;
			return Table.this.playerPositions.get(nextPlayerIndex);
		}

	}

}
