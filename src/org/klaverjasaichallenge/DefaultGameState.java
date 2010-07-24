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

	public DefaultGameState(List<Player> players) {
		this.players = players;
		this.roundsPlayed = new LinkedList<Round>();
	}

//
//
//	/**
//	 * @param phase
//	 *            the phase to set
//	 */
//	public void setPhase(GameStatePhases phase) {
//		this.phase = phase;
//	}
//
//	/**
//	 * @return the phase
//	 */
//	public GameStatePhases getPhase() {
//		return phase;
//	}
//
//	/**
//	 * Watch out: This method changes the current player! TODO This method is
//	 * really complex and not readable.
//	 */
//	public Player calculateCurrentPlayer() {
//		if (this.overrideCurrentPlayer != null) {
//			this.currentPlayer = this.overrideCurrentPlayer;
//			this.overrideCurrentPlayer = null;
//
//			// Clear the cards currently on the table
//			this.cardsOnTable = new Trick();
//		} else if (this.currentPlayer != null) {
//			int nextPlayerIndex = this.players.indexOf(currentPlayer) + 1;
//
//			// Start at the first player again if we had them all
//			if (nextPlayerIndex == PLAYER_AMOUNT) {
//				nextPlayerIndex = 0;
//			}
//
//			// Print the score of the trick that was played
//			if(this.trump != null && this.cardsOnTable != null) {
//				System.out.println("Stock score for this trick: " 
//						+ this.cardsOnTable.getStockScore(this.trump).getPoints());
//				System.out.println("Roem score for this trick: " 
//						+ this.cardsOnTable.getRoemScore(this.trump).getPoints());
//				System.out.println("Total score for this trick: " 
//						+ this.cardsOnTable.getTotalScore(this.trump).getPoints());
//			}
//
//			this.currentPlayer = this.players.get(nextPlayerIndex);
//		} else {
//			this.currentPlayer = this.players.get(0);
//		}
//
//		// End this phase if the player doesnt have cards anymore
//		if (this.hands.get(this.currentPlayer).amountOfCards() == 0) {
//			this.phase = GameStatePhases.FINISHED;
//		}
//
//		return this.currentPlayer;
//	}
//
//	public List<Card> getPlayerHand(Player player) {
//		return this.hands.get(player).getCards();
//	}


	
	/**
	 * The Personal Game State is an immutable object that can be returned to the players so that
	 * they can base their decisions on that information.
	 * 
	 * TODO Copy all the parameters, instead of passing them so it does not matter if the AI changes it
	 */
//	public PersonalGameState getPersonalGameState(Player player) {
//		return new PersonalGameState(new
//				LinkedList<Card>(this.hands.get(player).getCards()), this.cardsOnTable,
//				this.trump, this.leadingPlayer, this.phase);
//	}





	/**
	 * Just play your 16 rounds kk?!
	 */
	public void play() {
		
		Round prevRound;
		for(int currentRoundId = 0; currentRoundId < 16; currentRoundId++) {
			Round round = new Round(this.players, startingPlayer = prevRound.getWinner());
			round.play();
			round.getScores();
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
