/**
 *
 */
package org.klaverjasaichallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Player;

public class Table {
	private List<Team> teams;
	private List<Player> positions;
	private Player activePlayer;
	private Player roundStarter;

	public Table(final Team teamOne, final Team teamTwo) {
		this.teams = this.initializeTeams(teamOne, teamTwo);
		this.positions = this.initializePositions(teamOne, teamTwo);
		this.activePlayer = teamOne.getFirstPlayer();
		this.roundStarter = this.activePlayer;
	}

	/**
	 * When playing a trick, this method provides you with the player sitting
	 * next to the player that just threw a card on the table.
	 */
	public Table nextPlayer() {
		return new Table(this.teams, this.positions, getNextPlayer(this.activePlayer), this.roundStarter);
	}

	/**
	 * In the next trick, the winner of the previous trick is allowed to
	 * start.
	 */
	public Table nextTrick(final Player winner) {
		return new Table(this.teams, this.positions, winner, this.roundStarter);
	}

	public Table nextRound() {
		Player newRoundStarter = this.getNextPlayer(this.roundStarter);
		Table newTable =  new Table(this.teams,
				this.positions, newRoundStarter, newRoundStarter);
		return newTable;
	}

	public Player getActivePlayer() {
		return this.activePlayer;
	}

	public List<Team> getTeams() {
		return teams;
	}
	 
	public Team getTeamFromPlayer(final Player player) {
		Team selectedTeam = null;

		for(Team team : this.teams) {
			if(team.hasPlayer(player)) {
				selectedTeam = team;
			}
		}
		
		assert(selectedTeam != null);

		return selectedTeam;
	}
	
	public Team getOtherTeam(final Player player) {
		Team selectedTeam = null;

		for(Team team : this.teams) {
			if(!team.hasPlayer(player)) {
				selectedTeam = team;
			}
		}

		assert(selectedTeam != null);

		return selectedTeam;
	}

	public List<Player> getPlayers() {
		return this.positions;
	}

	private Table(final List<Team> teams, final List<Player> positions,
			final Player nextPlayer, final Player roundStarter) {
		this.teams = teams;
		this.positions = positions;
		this.activePlayer = nextPlayer;
		this.roundStarter = roundStarter;
	}

	private Player getNextPlayer(final Player currentPlayer) {
		int currentPlayerIndex = this.positions.indexOf(currentPlayer);
		int nextPlayerIndex = (currentPlayerIndex + 1) % this.positions.size();
		return this.positions.get(nextPlayerIndex);
	}

	private List<Team> initializeTeams(final Team teamOne, final Team teamTwo) {
		List<Team> teams = new ArrayList<Team>();
		teams.add(teamOne);
		teams.add(teamTwo);
		return teams;
	}

	private List<Player> initializePositions(final Team teamOne, final Team teamTwo) {
		List<Player> positions =  new LinkedList<Player>();
		positions.add(teamOne.getFirstPlayer());
		positions.add(teamTwo.getFirstPlayer());
		positions.add(teamOne.getSecondPlayer());
		positions.add(teamTwo.getSecondPlayer());
		return positions;
	}
	

}
