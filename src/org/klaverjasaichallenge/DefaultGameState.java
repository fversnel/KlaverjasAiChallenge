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
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Trick;
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
	private static final int MINIMUM_AMOUNT_TRUMPS_LEFT = 1;
	private static final int PLAYER_AMOUNT = 4;

	private final List<Player> players;
	private final Map<Player, Hand> hands;
	private List<Suit> availableTrumps;
	private GameStatePhases phase;

	private Suit trump;
	private Player overrideCurrentPlayer;
	private Player leadingPlayer;
	private Player currentPlayer;

	private Trick cardsOnTable;

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
	private Map<Player, Hand> generateHands(List<Player> players) {
		Deck deck = new Deck();
		Map<Player, Hand> hands = new HashMap<Player, Hand>();

		for (Player player : players) {
			hands.put(player, new Hand(deck));
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
			this.cardsOnTable = new Trick();
		} else if (this.currentPlayer != null) {
			int nextPlayerIndex = this.players.indexOf(currentPlayer) + 1;

			// Start at the first player again if we had them all
			if (nextPlayerIndex == PLAYER_AMOUNT)
				nextPlayerIndex = 0;

			this.currentPlayer = this.players.get(nextPlayerIndex);

		} else
			this.currentPlayer = this.players.get(0);

		// End this phase if the player doesnt have cards anymore
		if (this.hands.get(this.currentPlayer).amountOfCards() == 0) {
			this.phase = GameStatePhases.FINISHED;

			// Print the score of the trick that was played
			if(this.trump != null && this.cardsOnTable != null) {
				System.out.println("Stock score for this trick: " 
						+ this.cardsOnTable.getStockScore(this.trump).getPoints());
				System.out.println("Roem score for this trick: " 
						+ this.cardsOnTable.getRoemScore(this.trump).getPoints());
				System.out.println("Total score for this trick: " 
						+ this.cardsOnTable.getTotalScore(this.trump).getPoints());
			}
		}

		return this.currentPlayer;
	}

	public List<Card> getPlayerHand(Player player) {
		return this.hands.get(player).getCards();
	}

	public void playCard(Card card) throws Exception {
		if (this.hands.get(this.currentPlayer).drawCard(card) == null) {
			throw new Exception("The player played an invalid card! This card"
			+ " is not in his hand");
		}

		// Find out current suit
		Suit leadingSuit = cardsOnTable.getLeadingSuit();
		
		// If the leading player has already played a card (== first card)
		if(leadingSuit != null) {
			// If the player is not following suit
			if(!card.getSuit().equals(leadingSuit)) {
				// If the player cannot follow suit (!playerCanFollowSuit())
				if(!this.playerCanFollowSuit(this.currentPlayer, leadingSuit)) {
					// If the player is playing a trump card
					if(card.getSuit().equals(this.trump)) {
						Rank highestTrumpOnTable = getHighestTrump();
						// If the player is not raising a trump, but is able
						// to (playerCanRaiseTrump()), throw exception
						if(highestTrumpOnTable != null &&
								highestTrumpOnTable.getTrumpOrder().isHigherThan(card.getRank().getTrumpOrder())
								&& playerCanRaiseTrump(this.currentPlayer)) {
							throw new Exception("Player " + this.currentPlayer
									+ " can raise the trump but is not doing it."
									+ " Current trump: " + this.trump + ". Card played: "
									+ card);
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump(), throw exception
					else if(playerHasTrump(this.currentPlayer)) {
						throw new Exception("Player " + this.currentPlayer 
								+ " has trump but is not playing it. Current trump: "
								+ this.trump + ". Card played: " + card);
					}
				}
				// Else - Player can follow suit but is not, throw exception
				else  {
					throw new Exception("Player " + this.currentPlayer 
							+ " can follow suit but is not. Current suit: " 
							+ leadingSuit + ".  Card played: " + card);
				}
			}
		}
		
		this.cardsOnTable.addCard(this.currentPlayer, card);
	}
	
	/**
	 * The Personal Game State is an immutable object that can be returned to the players so that
	 * they can base their decisions on that information.
	 * 
	 * TODO Copy all the parameters, instead of passing them so it does not matter if the AI changes it
	 */
	public PersonalGameState getPersonalGameState(Player player) {
		return new PersonalGameState(new
				LinkedList<Card>(this.hands.get(player).getCards()), this.cardsOnTable,
				this.trump, this.leadingPlayer, this.phase);
	}

	private List<Suit> createAvailableTrumps() {
		return Card.getSuits();
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
		Hand playerHand = hands.get(player);
		return playerHand.hasSuit(suit);
	}
	
	/**
	 * This function checks whether a player can play a trump card
	 * 
	 * @param player
	 * @return True if the player has a trump card
	 */
	private boolean playerHasTrump(Player player) {
		Hand playerHand = hands.get(player);
		return playerHand.hasSuit(this.trump);
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
		for (Card card : hands.get(player).getCards()) {
			// If the player has a class of the same suit as the trump suit
			if (card.getSuit().equals(this.trump) 
					&& (highestTrumpOnTable == null 
					|| card.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder()))) {
				// Player can raise trump
				return true;
			}
		}

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
		for(Card cardOnTable : this.cardsOnTable.getCards()) {
			// If this card is a trump and higher ranked then current top ranked trump
			if (cardOnTable.getSuit().equals(this.trump) &&
					(highestTrumpOnTable == null ||
					cardOnTable.getRank().getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder()))) {
				highestTrumpOnTable = cardOnTable.getRank();
			}
		}

		return highestTrumpOnTable;
	}	

}
