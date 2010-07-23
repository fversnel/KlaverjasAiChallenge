package org.klaverjasaichallenge.shared;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.klaverjasaichallenge.Deck;
import org.klaverjasaichallenge.shared.Action;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Round {
	private static final int AMOUNT_OF_TRICKS = 8;

	private final List<Action> actions;
	
	private final Map<Player, Hand> hands;
	private final List<Trick> tricksPlayed;
	
	private final List<Player> players;
	
	private final List<Suit> availableTrumps;
	
	private static final int MINIMUM_AMOUNT_TRUMPS_LEFT = 1;
	private static final int PLAYER_AMOUNT = 4;
	
	private Suit trump;
	private Player overrideCurrentPlayer;
	private Player leadingPlayer;
	private Player currentPlayer;
	private Trick cardsOnTable;	


	public Round(List<Player> players) {
		this.actions = this.createActions();
		this.tricksPlayed = new LinkedList<Trick>();
		this.hands = generateHands(players);
		this.availableTrumps = Card.getSuits();
		this.players = players;
	}

	public void play() {
		
		/**
		 * Veel plezier
		 */
		for(Player player : players) {
			Hand playersHand = this.hands.get(player);
			boolean playerFeelsLikeIt = player.giveCards(playersHand.getCards());
		}
		
		/**
		 * Action: Draw Trump
		 */
		Player playerAcceptedTrump = null;
		do {
			Suit drawnTrump = null;
			try {
				drawnTrump = this.drawTrump();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			// TODO Which player may go first?!
			for(Player player : players) {
				if(player.playOnTrump(drawnTrump))
					playerAcceptedTrump = player;
			}
		} while(playerAcceptedTrump == null);
		
		/**
		 * Action: Trick or treat
		 */
		for(int trickId = 0; trickId < AMOUNT_OF_TRICKS; trickId ++) {
			Trick trick = new Trick();
			
			// TODO Which player may go first?!
			for(Player player : players) {
				Card cardPlayed = player.getCard();
				
				// TODO Rules of playCard() implement here
				
				trick.addCard(player, cardPlayed)
				
			}
		}
		
		
//		for(Action action : actions) {
//			action.perform(this, null);
//		}
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
	
	private List<Action> createActions() {
		List<Action> actions = new LinkedList<Action>();

		// These are ideas for the actions in a round:
		// Give all players their cards:
		//actions.add(new CreateHandsAction());
		
		// In this round the trump is drawn, players are allowed to choose
		//	 trump. if all players refuse, a new trump is drawn.
		// The ending state of this round is: a trump and a team that accepts
		//	 it.
		//		actions.add(new DrawTrumpAction());
		//
		//		After that eight tricks will be played.
		//		for(int i = 1; i <= AMOUNT_OF_TRICKS; i++) {
		//			actions.add(new TrickAction());
		//		}
		//
		//		A total score will be calculated to define the winner.
		//		actions.add(new RoundEndsAction());
		//
		//		return actions;
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
	 * 
	 */
	public Map<Player,Points> getScores() {
		Map<Player,Points> scores = new HashMap<Player,Points>();
		
		for(Trick trick : tricksPlayed) {
			
		}
		
		return null;
	}



}
