/**
 *
 */
package org.klaverjasaichallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * Information about the current state of the Game
 *
 * Do not return this to the Players, use getPersonalGameState() instead.
 *
 * @author Joost Pastoor
 *
 */
public class DefaultGameState {
	private static final int MAX_ROUNDS = 16;

	private Team team1;
	private Team team2;
	private Points team1Points;
	private Points team2Points;
	private List<Round> roundsPlayed;

	/**
	 * @param team1
	 * @param team2
	 */
	public DefaultGameState(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
		this.team1Points = new Points();
		this.team2Points = new Points();
		
		this.roundsPlayed = new LinkedList<Round>();
	}

	/**
	 * Just play your 16 rounds kk?!
	 */
	public void play() {
		
		Table table = new Table(this.team1, this.team2);

		for(int currentRoundId = 1; currentRoundId <= MAX_ROUNDS; currentRoundId++) {
			System.out.println("- Starting round: " + currentRoundId + " with Players: " + table.getPlayers());

			Round round = new Round(table);
			round.play();
			team1Points = Points.plus(team1Points, round.getScore(team1).getTotalScore());
			team2Points = Points.plus(team2Points, round.getScore(team2).getTotalScore());

			this.roundsPlayed.add(round);

			// Change the order of the players
			table = table.nextRound();
		}		
	}
	
	public Points getTeam1Points() {
		return this.team1Points;
	}
	
	public Points getTeam2Points() {
		return this.team2Points;
	}

	private List<Player> moveOrderUp(List<Player> oldList) {
		List<Player> newList = new ArrayList<Player>();

		newList.add(0,oldList.get(3));
		newList.add(1,oldList.get(0));
		newList.add(2,oldList.get(1));
		newList.add(3,oldList.get(2));

		return newList;
	}
}
