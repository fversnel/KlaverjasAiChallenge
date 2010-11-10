/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.score.Score;

public class Table {
	private Map<Team, Score> score;
	private List<Player> positions;
	private Player activePlayer;
	private Player roundStarter;

	public Table(final Team teamOne, final Team teamTwo) {
		this.score = this.initializeScore(teamOne, teamTwo);
		this.positions = this.initializePositions(teamOne, teamTwo);
		this.activePlayer = teamOne.getFirstPlayer();
		this.roundStarter = this.activePlayer;
	}

	/**
	 * When playing a trick, this method provides you with the player sitting
	 * next to the player that just threw a card on the table.
	 */
	public Table nextPlayer() {
		return new Table(this.score, this.positions, getNextPlayer(this.activePlayer), this.roundStarter);
	}

	/**
	 * In the next trick, the winner of the previous trick is allowed to
	 * start.
	 */
	public Table nextTrick(final Player winner) {
		return new Table(this.score, this.positions, winner, this.roundStarter);
	}

	public Table nextRound() {
		Player newRoundStarter = this.getNextPlayer(this.roundStarter);
		Table newTable =  new Table(this.resetScore(this.score),
				this.positions, newRoundStarter, newRoundStarter);
		return newTable;
	}

	public Player getActivePlayer() {
		return this.activePlayer;
	}

	public Score getScore(final Team team) {
		return this.score.get(team);
	}

	private Table(final Map<Team, Score> score, final List<Player> positions,
			final Player nextPlayer, final Player roundStarter) {
		this.score = score;
		this.positions = positions;
		this.activePlayer = nextPlayer;
		this.roundStarter = roundStarter;
	}

	private Player getNextPlayer(final Player currentPlayer) {
		int currentPlayerIndex = this.positions.indexOf(currentPlayer);
		int nextPlayerIndex = (currentPlayerIndex + 1) % this.positions.size();
		return this.positions.get(nextPlayerIndex);
	}

	private Map<Team, Score> resetScore(final Map<Team, Score> oldScore) {
		Map<Team, Score> newScore = new HashMap<Team, Score>();
		for(Team team : oldScore.keySet()) {
			newScore.put(team, new Score());
		}
		return newScore;
	}

	private Map<Team, Score> initializeScore(final Team teamOne, final Team teamTwo) {
		Map<Team, Score> initScore = new HashMap<Team, Score>();
		initScore.put(teamOne, new Score());
		initScore.put(teamTwo, new Score());
		return initScore;
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
