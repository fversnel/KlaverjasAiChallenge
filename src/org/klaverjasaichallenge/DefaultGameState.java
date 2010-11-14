/**
 *
 */
package org.klaverjasaichallenge;

// Import log4j classes.
import org.apache.log4j.Logger;

import org.klaverjasaichallenge.shared.Points;

/**
 * Information about the current state of the Game
 */
public class DefaultGameState {
	private static final int MAX_ROUNDS = 16;

	private final Team team1;
	private final Team team2;
	private Points team1Points;
	private Points team2Points;

	private Logger logger;

	/**
	 * Default constructor
	 *
	 * @param team1
	 * @param team2
	 */
	public DefaultGameState(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;

		// Initialize team points at 0
		this.team1Points = new Points(0);
		this.team2Points = new Points(0);

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	/**
	 * Play coordinates the running of 16 consecutive rounds.
	 */
	public void play() {

		// Create a table object to coordinate the round starters
		Table table = new Table(this.team1, this.team2);

		for(int currentRoundId = 1; currentRoundId <= MAX_ROUNDS; currentRoundId++) {
			this.logger.debug("- Starting round: " + currentRoundId + " with " +
					"Players: " + table.getPlayers());

			// Play the round
			Round round = new Round(table);
			round.play();

			// Sum up the round score to the total score for each team this game
			this.team1Points = Points.plus(this.team1Points, round.getScore(this.team1).getTotalScore());
			this.team2Points = Points.plus(this.team2Points, round.getScore(this.team2).getTotalScore());

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
}
