/**
 * 
 */
package org.klaverjasaichallenge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.klaverjasaichallenge.shared.PersonalGameState;
import org.klaverjasaichallenge.shared.Player;
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

	/**
	 * TODO Should cardsOnTable become a Trick? Or is a Trick only used for finished 
	 */
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

		this.trump = chosenTrump;
		
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
	 * Watch out: This method changes the current player! TODO This method is
	 * really complex and not readable.
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
		if (this.hands.get(this.currentPlayer).size() == 0) {
			this.phase = GameStatePhases.FINISHED;
		}

		return this.currentPlayer;
	}

	public List<Card> getPlayerHand(Player player) {
		return this.hands.get(player);
	}

	public void playCard(Card card) throws Exception {
		if (!this.hands.get(this.currentPlayer).remove(card))
			throw new Exception("The player played an invalid card! This card is not in his hand");

		// Find out current suit
		Card leadingCard = cardsOnTable.get(this.leadingPlayer);
		
		// If the leading player has already played a card (== first card)
		if(leadingCard != null) {
			Suit currentSuit = leadingCard.getSuit();
			// If the player is not following suit
			if(!card.getSuit().getClass().equals(currentSuit.getClass())) {
				// If the player cannot follow suit (!playerCanFollowSuit())
				if(!playerCanFollowSuit(this.currentPlayer,currentSuit)) {
					// If the player is playing a trump card
					if(card.getSuit().getClass().equals(this.trump.getClass())) {
						Rank highestTrumpOnTable = getHighestTrump();
						// If the player is not raising a trump, but is able to (playerCanRaiseTrump()), throw exception
						if(highestTrumpOnTable != null && highestTrumpOnTable.getTrumpOrder().isHigherThan(card.getRank().getTrumpOrder()) && playerCanRaiseTrump(this.currentPlayer))
							throw new Exception("Player " + this.currentPlayer + " can raise the trump but is not doing it. Current trump: " + this.trump + ". Card played: " + card);
					}
					// Else - Player is not playing a trump card, but is able to (playerHasTrump(), throw exception
					else if(playerHasTrump(this.currentPlayer)) throw new Exception("Player " + this.currentPlayer + " has trump but is not playing it. Current trump: " + this.trump + ". Card played: " + card);
				}
				// Else - Player can follow suit but is not, throw exception
				else throw new Exception("Player " + this.currentPlayer + " can follow suit but is not. Current suit: " + currentSuit + ". Card played: " + card);
			}
		}
		
		this.cardsOnTable.put(this.currentPlayer, card);
	}
	
	/**
	 * The Personal Game State is an immutable object that can be returned to the players so that
	 * they can base their decisions on that information.
	 */
	public PersonalGameState getPersonalGameState(Player player) {
		return new PersonalGameState(this.hands.get(player), this.cardsOnTable, this.trump, this.leadingPlayer, this.phase);
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

	/**
	 * This function checks whether a player can play a card of
	 * given suit or not.
	 * 
	 * @param player
	 * @param suit
	 * @return True if the player can follow suit
	 */
	private boolean playerCanFollowSuit(Player player, Suit suit) {
		// Loop through all cards of the player
		for (Card card : hands.get(player))
			// If the player has a class of the same suit as the provided suit
			if (card.getSuit().getClass().equals(suit.getClass()))
				// Player can follow suit
				return true;

		// All cards have been checked but none found of provided suit, player
		// cannot follow
		return false;
	}
	
	/**
	 * This function checks whether a player can play a trump card
	 * 
	 * @param player
	 * @return True if the player has a trump card
	 */
	private boolean playerHasTrump(Player player) {
		// Loop through all cards of the player
		for (Card card : hands.get(player))
			// If the player has a class of the same suit as the trump suit
			if (card.getSuit().getClass().equals(this.trump.getClass()))
				// Player can follow suit
				return true;

		// All cards have been checked but none found of trump suit, player
		// has no trump cards
		return false;
	}	
	
	/**
	 * This function checks whether a player can play a trump card
	 * that is higher then the current played trumps.
	 * 
	 * @param player
	 * @return True if the player can raise the trump
	 * 
	 * TODO Raise trump might not be the perfect naming ...
	 */
	private boolean playerCanRaiseTrump(Player player) {
		Rank highestTrumpOnTable = getHighestTrump();
		
		// Loop through all cards of the player
		for (Card card : hands.get(player))
			// If the player has a class of the same suit as the trump suit
			if (card.getSuit().getClass().equals(this.trump.getClass()) && (highestTrumpOnTable == null || card.getRank().getTrumpPoints().getPoints() > highestTrumpOnTable.getTrumpPoints().getPoints()))
				// Player can raise trump
				return true;

		// All cards have been checked but none found of trump suit, player
		// has no trump cards
		return false;
	}

	/**
	 * @return
	 */
	private Rank getHighestTrump() {
		Rank highestTrumpOnTable = null;
		
		// Loop through the currently played cards
		for(Card cardOnTable : this.cardsOnTable.values()) {
			// If this card is a trump and higher ranked then current top ranked trump
			if (cardOnTable.getSuit().getClass().equals(this.trump.getClass()) && (highestTrumpOnTable == null || cardOnTable.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())))
				highestTrumpOnTable = cardOnTable.getRank();
		}
		return highestTrumpOnTable;
	}	

}
