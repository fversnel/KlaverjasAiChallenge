/**
 *
 */
package org.klaverjasaichallenge;

// Import log4j classes.
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

// Import AI's.
import nl.joostpastoor.klaverjasai.JoostAI;
import org.klaverjasaichallenge.ai.*;

// Import the necessary klaverjas classes to get started.
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * @author Joost
 *
 */
public class KlaverjasAIChallenge {
	private final static int TEAM_SIZE = 2;
	private final static String AI_PACKAGE_NAME = "org.klaverjasaichallenge.ai.";

	// Define a static logger variable so that it references the
	// Logger instance named "KlaverjasLogger".
	private final static Logger logger = Logger.getLogger("KlaverjasLogger");

	private static Points team1Points = new Points();
	private static Points team2Points = new Points();

	public static void main(String[] args) {
		initializeLogger(Level.INFO);
		System.setSecurityManager(initializeSecurityManager());

		if(args.length == 3){
			String firstAI = args[0];
			String secondAI = args[1];
			int numberOfGames = Integer.valueOf(args[2]);

			if(numberOfGames <= 0) {
				throw new RuntimeException("Number of games has to be higher than 0");
			}

			Team team1 = createTeam(AI_PACKAGE_NAME + firstAI);
			Team team2 = createTeam(AI_PACKAGE_NAME + secondAI);

			play(team1, team2, numberOfGames, new RotterdamRuleSet());

		} else {
			logger.error("You have to pass three program arguments in order " +
					"for the system to run properly.");
			printHelpMessage();
		}
	}

	private static void play(final Team team1, final Team team2,
			final int numberOfGames, final RuleSet ruleSet) {
		// Create a table object to coordinate the round starters
		Table table = new Table(team1, team2);

		for(int currentGameId = 1; currentGameId <= numberOfGames; currentGameId++) {
			logger.debug("Starting game: " + currentGameId);

			Points team1GamePoints = new Points();
			Points team2GamePoints = new Points();

			for(int currentRoundId = 1; currentRoundId <= ruleSet.getNumberOfRounds(); currentRoundId++) {
				logger.debug("- Starting round: " + currentRoundId + " with " +
						"Players: " + table.getPlayers());

				// Play the round
				Round round = new Round(table, ruleSet);
				round.play();

				// Sum up the round score to the total score for each team this game
				team1GamePoints = Points.plus(team1GamePoints, round.getScore(team1).getTotalScore());
				team2GamePoints = Points.plus(team2GamePoints, round.getScore(team2).getTotalScore());

				// Change the order of the players
				table = table.nextRound();

				logger.debug("Game scores:");
				logger.debug(team1 + " scored " + team1GamePoints + " points.");
				logger.debug(team2 + " scored " + team2GamePoints + " points.");
			}

			// Sum up the game score.
			team1Points = Points.plus(team1Points, team1GamePoints);
			team2Points = Points.plus(team2Points, team2GamePoints);
		}

		logger.info("Overall score for " + numberOfGames + " games:");
		logger.info(team1 + " scored " + team1Points + " points.");
		logger.info(team2 + " scored " + team2Points + " points.");
		logger.info("Average score per game:");
		logger.info(team1 + " scored " + Points.divide(team1Points, new Points(numberOfGames)) + " points.");
		logger.info(team2 + " scored " + Points.divide(team2Points, new Points(numberOfGames)) + " points.");
	}

	private static void initializeLogger(Level level) {
		// Set up a simple configuration that logs on the console.
		BasicConfigurator.configure();
		// There are two levels that are currently useful:
		// DEBUG - Displays all messages
		// INFO - Only displays the AI battle's end results.
		logger.setLevel(level);
	}

	private static SecurityManager initializeSecurityManager() {
		return new SecurityManager();
	}

	private static void printHelpMessage() {
		System.out.println("The program takes these arguments:\n" +
				"1st argument: name of the first AI\n"+
				"2nd argument: name of the second AI\n" +
				"3rd argument: the number of games that will be played.\n" +
				"The first AI will form a team with two copies of itself against " +
				"the team given in the second argument.");
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
