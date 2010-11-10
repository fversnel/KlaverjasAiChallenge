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

	private List<Player> players;
	private List<Round> roundsPlayed;

	public DefaultGameState(List<Player> players) {

		// Put the players in a hashmap that is ordered
		this.players = players;
		this.roundsPlayed = new LinkedList<Round>();
	}

	/**
	 * Just play your 16 rounds kk?!
	 */
	public void play() {

		for(int currentRoundId = 1; currentRoundId <= MAX_ROUNDS; currentRoundId++) {
			System.out.println("- Starting round: " + currentRoundId + " with Players: " + players);

			Round round = new Round(this.players);
			round.play();
			//round.getScores();

			this.roundsPlayed.add(round);

			// Change the order of the players
			this.players = this.moveOrderUp(this.players);
		}
	}

	/**
	 *
	 */
	public Map<Player,Points> getScores() {
		Map<Player,Points> scores = new HashMap<Player,Points>();

		for(Round round : roundsPlayed) {

		}

		return null;
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
