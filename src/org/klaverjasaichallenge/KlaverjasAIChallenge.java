/**
 *
 */
package org.klaverjasaichallenge;

// Import log4j classes.
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

// Import AI's.
import nl.joostpastoor.klaverjasai.JoostAI;
import org.klaverjasaichallenge.ai.*;

// Import the necessary klaverjas classes to get started.
import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * @author Joost
 *
 */
public class KlaverjasAIChallenge {
	private final static int PLAY_GAMES = 10;
	private final static int TEAM_SIZE = 2;
	private final static String AI_PACKAGE_NAME = "org.klaverjasaichallenge.ai.";

	// Define a static logger variable so that it references the
	// Logger instance named "KlaverjasLogger".
	static Logger logger = Logger.getLogger("KlaverjasLogger");

	public static void main(String[] args) {

		// Set up a simple configuration that logs on the console.
     	BasicConfigurator.configure();
		logger.setLevel(Level.INFO);

		Points team1Points = new Points();
		Points team2Points = new Points();
		
		Team team1 = createTeam(AI_PACKAGE_NAME + "PipoAI");
		Team team2 = createTeam(AI_PACKAGE_NAME + "StupidButLegalAI");

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

		logger.info("Overall score for " + PLAY_GAMES + " games:");
		logger.info(team1 + " scored " + team1Points);
		logger.info(team2 + " scored " + team2Points);
		logger.info("Average score per game:");
		logger.info(team1 + " scored " + Points.divide(team1Points, new Points(PLAY_GAMES)));
		logger.info(team2 + " scored " + Points.divide(team2Points, new Points(PLAY_GAMES)));
	}
	
	private static Team createTeam(final String aiName) {
		Team team = null;
		try {			
			for(int i = 0; i < TEAM_SIZE; i++) {
				Class<Player> newPlayer = (Class<Player>) Class.forName(aiName);
				try {
					team = new Team(newPlayer.newInstance(), newPlayer.newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return team;
	}
}
