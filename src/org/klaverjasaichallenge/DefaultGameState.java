/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.PersonalGameState;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Round;
import org.klaverjasaichallenge.shared.PersonalGameState.GameStatePhases;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

/**
 * Information about the current state of the Game
 * 
 * Do not return this to the Players, use getPersonalGameState() instead.
 * 
 * @author Joost Pastoor
 * 
 */
public class DefaultGameState {

	private final Map<Order, Player> players;
	private List<Round> roundsPlayed;

	public DefaultGameState(List<Player> playerList) {
		
		// Put the players in a hashmap that is ordered
		this.players = new HashMap<Order, Player>();
		for(int playerId = 0; playerId < this.players.size(); playerId++) {
			this.players.put(new Order(playerId), playerList.get(playerId));
		}
		
		this.roundsPlayed = new LinkedList<Round>();
	}

	/**
	 * Just play your 16 rounds kk?!
	 */
	public void play() {
		
		Round prevRound;
		for(int currentRoundId = 0; currentRoundId < 16; currentRoundId++) {
			System.out.println("- Starting round: " + currentRoundId);

			Round round = new Round(this.players);
			round.play();
			round.getScores();
			
			this.roundsPlayed.add(round);
			
			// TODO Maybe nicer to store a list with rounds, instead of only keeping the last
			prevRound = round;
		}
	}




	/**
	 * 
	 */
	public Map<Player,Points> getScores() {
		Map<Player,Points> scores = new HashMap<Player,Points>();
		
		for(Round round : roundsPlayed) {
			
		}
		
		return null;
	}	

}
