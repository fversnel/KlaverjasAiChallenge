/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.List;
import java.util.LinkedList;

import nl.joostpastoor.joostklaverjasai.JoostAI;

import org.klaverjasaichallenge.shared.PersonalGameState.GameStatePhases;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

/**
 * @author Joost
 * 
 */
public class KlaverjasAIChallenge {
	
	private final static int PLAY_GAMES = 10;

	public static void main(String[] args) {

		List<Player> players = new LinkedList<Player>();
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI());
		players.add(new JoostAI());
		
		for(int currentGameId = 0; currentGameId < PLAY_GAMES; currentGameId++) {	
			System.out.println("Starting game: " + currentGameId);
			DefaultGameState game = new DefaultGameState(players);
			game.play();
			game.getScores();
		}
	}
}
