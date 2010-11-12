/**
 *
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import nl.joostpastoor.klaverjasai.JoostAI;
import nl.joostpastoor.stupidbutlegalai.StupidButLegalAI;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;

/**
 * @author Joost
 *
 */
public class KlaverjasAIChallenge {

	private final static int PLAY_GAMES = 1000;

	public static void main(String[] args) {
		Points team1Points = new Points();
		Points team2Points = new Points();

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
			
			team1Points = Points.plus(team1Points, game.getTeam1Points());
			team2Points = Points.plus(team2Points, game.getTeam2Points());
			
			System.out.println("Game Scores");
			System.out.println(team1 + " scored " + game.getTeam1Points());
			System.out.println(team2 + " scored " + game.getTeam2Points());
		}
		
		System.out.println("Overall Score");
		System.out.println(team1 + " scored " + team1Points);
		System.out.println(team2 + " scored " + team2Points);
	}
}
