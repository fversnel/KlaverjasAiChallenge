/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

// Import log4j classes.
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

// Import AI's.
import nl.joostpastoor.klaverjasai.JoostAI;
import nl.joostpastoor.stupidbutlegalai.StupidButLegalAI;

// Import the necessary klaverjas classes to get started.
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * @author Joost
 *
 */
public class KlaverjasAIChallenge {

	private final static int PLAY_GAMES = 10;

	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	static Logger logger = Logger.getLogger("KlaverjasLogger");

	public static void main(String[] args) {
     	// Set up a simple configuration that logs on the console.
     	BasicConfigurator.configure();
		logger.setLevel(Level.INFO);

		Points team1Points = new Points();
		Points team2Points = new Points();

		Player player_t1_1 = new JoostAI();
		Player player_t1_2 = new JoostAI();
		Player player_t2_1 = new StupidButLegalAI();
		Player player_t2_2 = new StupidButLegalAI();

		Team team1 = new Team(player_t1_1, player_t1_2);
		Team team2 = new Team(player_t2_1, player_t2_2);

		for(int currentGameId = 0; currentGameId < PLAY_GAMES; currentGameId++) {
			logger.debug("Starting game: " + currentGameId);
			DefaultGameState game = new DefaultGameState(team1, team2);
			game.play();

			team1Points = Points.plus(team1Points, game.getTeam1Points());
			team2Points = Points.plus(team2Points, game.getTeam2Points());

			logger.debug("Game Scores");
			logger.debug(team1 + " scored " + game.getTeam1Points());
			logger.debug(team2 + " scored " + game.getTeam2Points());
		}

		logger.info("Overall Score");
		logger.info(team1 + " scored " + team1Points);
		logger.info(team2 + " scored " + team2Points);
	}
}
