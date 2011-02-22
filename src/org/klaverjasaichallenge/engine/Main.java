/**
 *
 */
package org.klaverjasaichallenge.engine;

// Import log4j classes.
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;

// Import the necessary klaverjas classes to get started.
import org.klaverjasaichallenge.engine.round.Round;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.RuleSet;

/**
 *
 * @author Joost
 */
public class Main {
	private final static int TEAM_SIZE = 2;

	private final static Logger logger = Logger.getLogger(Main.class.getName());

	private static Score team1Score = new Score();
	private static Score team2Score = new Score();

	public static void main(String[] args) {
		initializeLogger(Level.DEBUG);

		System.setSecurityManager(new SecurityManager());

		if(args.length == 3){
			final String firstAI = args[0];
			final String secondAI = args[1];
			final int numberOfGames = Integer.valueOf(args[2]);

			if(numberOfGames <= 0) {
				throw new RuntimeException("Number of games has to be higher than 0");
			}

			final Team team1 = createTeam(firstAI);
			final Team team2 = createTeam(secondAI);

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

			Score team1GameScore = new Score();
			Score team2GameScore = new Score();

			for(int currentRoundId = 1; currentRoundId <= ruleSet.getNumberOfRounds(); currentRoundId++) {
				logger.debug("- Starting round: " + currentRoundId + " with " +
						"Players: " + table);

				// Play the round
				Round round = new Round(table, ruleSet);
				round.play();

				// Sum up the round score to the total score for each team this game
				team1GameScore = Score.plus(team1GameScore, round.getScore(team1));
				team2GameScore = Score.plus(team2GameScore, round.getScore(team2));

				// Change the order of the players
				table.nextRound();

				logger.debug("Game scores:");
				logger.debug(team1 + " scored " + team1GameScore + " points.");
				logger.debug(team2 + " scored " + team2GameScore + " points.");
			}

			// Sum up the game score.
			team1Score = Score.plus(team1Score, team1GameScore);
			team2Score = Score.plus(team2Score, team2GameScore);
		}

		logger.info("Overall score for " + numberOfGames + " games:");
		logger.info(team1 + " scored " + team1Score + " points.");
		logger.info(team2 + " scored " + team2Score + " points.");
		logger.info("Average score per game:");
		logger.info(team1 + " scored " +
				Points.divide(team1Score.getTotalScore(), new
					Points(numberOfGames)) + " points.");
		logger.info(team2 + " scored " +
				Points.divide(team2Score.getTotalScore(), new
					Points(numberOfGames)) + " points.");
	}

	private static void initializeLogger(Level level) {
		// Set up a simple configuration that logs on the console.
		BasicConfigurator.configure();
		// There are two levels that are currently useful:
		// DEBUG - Displays all messages
		// INFO - Only displays the AI battle's end results.
		logger.setLevel(level);
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
				team = new Team(newPlayer.newInstance(), newPlayer.newInstance());
			}
		} catch (InstantiationException e) {
			logger.error("Unable to instantiate AI " + aiName, e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			logger.error("Illegal access to " + aiName, e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			logger.error("AI " + aiName + " not found", e);
			throw new RuntimeException(e);
		}

		return team;
	}

}
