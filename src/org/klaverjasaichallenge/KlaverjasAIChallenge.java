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

		List<Player> players = new LinkedList<Player>();
		players.add(new JoostAI());
		players.add(new StupidButLegalAI());
		players.add(new JoostAI());
		players.add(new StupidButLegalAI());

		for(int currentGameId = 0; currentGameId < PLAY_GAMES; currentGameId++) {
			System.out.println("Starting game: " + currentGameId);
			DefaultGameState game = new DefaultGameState(players);
			game.play();
			game.getScores();
		}
	}
}
