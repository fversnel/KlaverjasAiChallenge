/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.Suit;

/**
 * @author Joost
 * 
 */
public class DefaultGameState implements GameState {

	private final List<Player> players;
	private final Map<Player, List<Card>> hands;
	private List<Suit> drawnSuits;
	private GameStatePhases phase;

	private Suit trump;
	private Player overrideCurrentPlayer;
	private Player currentPlayer;

	public DefaultGameState(List<Player> players) {
		this.players = players;
		this.setPhase(GameStatePhases.CHOOSING_TRUMP);
		this.drawnSuits = new LinkedList<Suit>();

		/**
		 * Give players their cards
		 */
		this.hands = generateHands(this.players);
	}

	/**
	 * This function creates a Deck object and gives the cards to the different
	 * players so they all have a hand of 8 cards, which is returned.
	 */
	private Map<Player, List<Card>> generateHands(List<Player> players) {
		Deck deck = new Deck();
		Map<Player, List<Card>> hands = new HashMap<Player, List<Card>>();

		for (Player player : players) {
			List<Card> hand = new LinkedList<Card>();

			// Draw 8 cards for the hand
			for (int i = 1; i <= 8; i++) {
				hand.add(deck.drawCard());
			}

			// Add this players hand to the hands variable
			hands.put(player, hand);
		}

		return hands;
	}

	public Suit drawTrump() throws Exception {
		if (this.drawnSuits.size() == 3)
			throw new Exception("May only draw three trumps");

		// Put all suits in a list
		Suit[] suits = Card.getSuits();
		Random seed = new Random(System.nanoTime());
		Suit chosenSuit = suits[seed.nextInt(suits.length)];

		boolean suitAlreadyDrawn = false;

		// If this trump has not been chosen yet
		for (Suit drawnSuit : this.drawnSuits) {
			if (drawnSuit == chosenSuit)
				suitAlreadyDrawn = true;
		}

		if (suitAlreadyDrawn == false) {
			this.drawnSuits.add(chosenSuit);
			this.trump = chosenSuit;
			return chosenSuit;
		} else
			return drawTrump();
	}

	public void playerAcceptedTrump(Player player) {
		this.overrideCurrentPlayer = player;
		this.phase = GameStatePhases.PLAYING;
	}

	/**
	 * @param phase
	 *            the phase to set
	 */
	public void setPhase(GameStatePhases phase) {
		this.phase = phase;
	}

	/**
	 * @return the phase
	 */
	public GameStatePhases getPhase() {
		return phase;
	}

	/**
	 * Watch out: This method changes the current player!
	 */
	public Player calculateCurrentPlayer() {

		if (this.overrideCurrentPlayer != null) {
			this.currentPlayer = this.overrideCurrentPlayer;
			this.overrideCurrentPlayer = null;
		} else if (this.currentPlayer != null) {
			int nextPlayerIndex = this.players.indexOf(currentPlayer) + 1;

			// Start at the first player again if we had them all
			if (nextPlayerIndex == 4)
				nextPlayerIndex = 0;

			this.currentPlayer = this.players.get(nextPlayerIndex);

		} else
			this.currentPlayer = this.players.get(0);

		// End this phase if the player doesnt have cards anymore
		if(this.hands.get(this.currentPlayer).size() == 0) {
			this.phase = GameStatePhases.FINISHED;
		}
		
		return this.currentPlayer;
	}
	
	public List<Card> getPlayerHand(Player player) {
		return this.hands.get(player);
	}
	
	public void playCard(Card card) throws Exception {
		if(!this.hands.get(this.currentPlayer).remove(card))
			throw new Exception("The player played an invalid card! This card is not in his hand");
	}
}
