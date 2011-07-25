/**
 *
 */
package org.klaverjasaichallenge.engine;

// Import log4j classes.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Import the necessary klaverjas classes to get started.
import org.klaverjasaichallenge.engine.round.Round;
import org.klaverjasaichallenge.engine.round.data.RoundResult;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.ruleset.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Main {
	private final static String AI_PACKAGE = "org.klaverjasaichallenge.ai.";
	
	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	private static Score team1Score = new Score();
	private static Score team2Score = new Score();

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());

		if(args.length == 3){
			final String firstAI = args[0];
			final String secondAI = args[1];
			final int numberOfGames = Integer.valueOf(args[2]);

			if(numberOfGames <= 0) {
				throw new RuntimeException("Number of games has to be higher than 0");
			}

			ClassLoader aiLoader = KlaverJasAI.class.getClassLoader();
			final Team team1 = createTeam(AI_PACKAGE + firstAI, aiLoader);
			final Team team2 = createTeam(AI_PACKAGE + secondAI, aiLoader);

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
				RoundResult roundResult = round.play();

				// Sum up the round score to the total score for each team this game
				team1GameScore = team1GameScore.plus(roundResult.getScore(team1));
				team2GameScore = team2GameScore.plus(roundResult.getScore(team2));

				// Change the order of the players
				table.nextRound();

				logger.debug("\nRound scores:\n" +
							team1 + " scored " + roundResult.getScore(team1) + " points.\n" +
							team2 + " scored " + roundResult.getScore(team2) + " points.");
			}

			// Sum up the game score.
			team1Score = team1Score.plus(team1GameScore);
			team2Score = team2Score.plus(team2GameScore);
		}

		logger.info("\nOverall score for " + numberOfGames + " games:\n" +
				team1 + " scored " + team1Score + " points.\n" +
				team2 + " scored " + team2Score + " points.\n" +
				"Average score per game:\n" +
				team1 + " scored " +
				Points.divide(team1Score.getTotalScore(), new
					Points(numberOfGames)) + " points.\n" +
				team2 + " scored " +
				Points.divide(team2Score.getTotalScore(), new
					Points(numberOfGames)) + " points.");
	}

	private static void printHelpMessage() {
		System.out.println("The program takes these arguments:\n" +
				"1st argument: name of the first AI\n"+
				"2nd argument: name of the second AI\n" +
				"3rd argument: the number of games that will be played.\n" +
				"The first AI will form a team with two copies of itself against " +
				"the team given in the second argument.");
	}

	private static Team createTeam(final String aiName, ClassLoader aiLoader) {
		try {
			@SuppressWarnings("unchecked")
			Class<KlaverJasAI> newPlayer = (Class<KlaverJasAI>) aiLoader.loadClass(aiName);
			return new Team(newPlayer.newInstance(), newPlayer.newInstance());
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
	}

}
