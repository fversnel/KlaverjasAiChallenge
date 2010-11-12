/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import nl.joostpastoor.klaverjasai.JoostAI;
import nl.joostpastoor.stupidbutlegalai.StupidButLegalAI;

import org.klaverjasaichallenge.shared.Player;

/**
 * @author Joost
 *
 */
public class KlaverjasAIChallenge {

	private final static int PLAY_GAMES = 1;

	public static void main(String[] args) {

		Player player_t1_1 = new JoostAI();
		Player player_t1_2 = new JoostAI();
		Player player_t2_1 = new StupidButLegalAI();
		Player player_t2_2 = new StupidButLegalAI();

		Team team1 = new Team(player_t1_1, player_t1_2);
		Team team2 = new Team(player_t2_1, player_t2_2);
		
		for(int currentGameId = 0; currentGameId < PLAY_GAMES; currentGameId++) {
			System.out.println("Starting game: " + currentGameId);
			DefaultGameState game = new DefaultGameState(team1, team2);
			game.play();
			game.getScores();
		}
	}
}
