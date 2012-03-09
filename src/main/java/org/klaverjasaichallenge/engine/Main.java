/**
 *
 */
package org.klaverjasaichallenge.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.klaverjasaichallenge.human.CommandLinePlayer;
import org.klaverjasaichallenge.shared.KlaverJasAI;
import org.klaverjasaichallenge.shared.ruleset.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;

import static org.klaverjasaichallenge.util.AiLoader.loadAi;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());

		if(args.length == 3){
			final String firstAI = args[0];
			final String secondAI = args[1];
			final int numberOfRounds = Integer.valueOf(args[2]);

			final Team team1 = new Team(loadAi(firstAI), loadAi(firstAI));
			final Team team2 = new Team(loadAi(secondAI), loadAi(secondAI));

			long playDuration = play(new RotterdamRuleSet(), team1, team2, numberOfRounds);
			logger.info("Duration: {}ms", playDuration);

		} else {
			logger.error("You have to pass three program arguments in order " +
					"for the system to run properly.");
			printHelpMessage();
		}
	}

	private static long play(final RuleSet ruleSet, final Team team1, final Team team2,
			final int numberOfRounds) {
		KlaverjasGame klaverjasGame = new KlaverjasGame(ruleSet, team1, team2, numberOfRounds);
		long startTime = System.currentTimeMillis();
		OverallScore overallScore = klaverjasGame.play();
		long duration = System.currentTimeMillis() - startTime;

		logger.info(String.format("Overall score for %d rounds:%n" +
				"%s scored %s,%n%d plays, %d wets, %d marches%n" +
				"%s scored %s,%n%d plays, %d wets, %d marches",
				numberOfRounds,
				team1, overallScore.getScore(team1),
				overallScore.getNumberOfPlays(team1),
				overallScore.getNumberOfWets(team1),
				overallScore.getNumberOfMarchings(team1),
				team2, overallScore.getScore(team2),
				overallScore.getNumberOfPlays(team2),
				overallScore.getNumberOfWets(team2),
				overallScore.getNumberOfMarchings(team2)));

		return duration;
	}

	private static void printHelpMessage() {
		System.out.println("The program takes these arguments:\n" +
				"1st argument: name of the first AI\n"+
				"2nd argument: name of the second AI\n" +
				"3rd argument: the number of rounds that will be played.\n" +
				"The first AI will form a team with two copies of itself against " +
				"the team given in the second argument.");
	}

}
