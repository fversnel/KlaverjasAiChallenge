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
	private static final int CARDS_IN_HAND = 8;
	private static final int MINIMUM_AMOUNT_TRUMPS_LEFT = 1;
	private static final int PLAYER_AMOUNT = 4;

	private final List<Player> players;
	private final Map<Player, List<Card>> hands;
	private List<Suit> availableTrumps;
	private GameStatePhases phase;

	private Suit trump;
	private Player overrideCurrentPlayer;
	private Player leadingPlayer;
	private Player currentPlayer;
	
	private Map<Player, Card> cardsOnTable;

	public DefaultGameState(List<Player> players) {
		this.players = players;
		this.leadingPlayer = null;

		this.setPhase(GameStatePhases.CHOOSING_TRUMP);

		this.availableTrumps = this.createAvailableTrumps();

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
			hands.put(player, this.drawHand(deck));
		}

		return hands;
	}

	public Suit drawTrump() throws Exception {
		if (this.availableTrumps.size() == MINIMUM_AMOUNT_TRUMPS_LEFT) {
			throw new Exception("Maximum amount of trumps drawn.");
		}

		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(availableTrumps.size());
		final Suit chosenTrump = availableTrumps.remove(trumpIndex);

		return chosenTrump;
	}

	public void playerAcceptedTrump(Player player) {
		this.overrideCurrentPlayer = player;
		this.leadingPlayer = player;
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
	 * TODO This method is really complex and not readable.
	 */
	public Player calculateCurrentPlayer() {
		if (this.overrideCurrentPlayer != null) {
			this.currentPlayer = this.overrideCurrentPlayer;
			this.overrideCurrentPlayer = null;
			// Clear the cards currently on the table
			this.cardsOnTable = new HashMap<Player, Card>();
		} else if (this.currentPlayer != null) {
			int nextPlayerIndex = this.players.indexOf(currentPlayer) + 1;

			// Start at the first player again if we had them all
			if (nextPlayerIndex == PLAYER_AMOUNT)
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

	private List<Suit> createAvailableTrumps() {
		return Card.getSuits();
	}

	private List<Card> drawHand(final Deck deck) {
		List<Card> hand = new LinkedList<Card>();
		for (int i = 1; i <= CARDS_IN_HAND; i++) {
			hand.add(deck.drawCard());
		}

		return hand;
	}

}
