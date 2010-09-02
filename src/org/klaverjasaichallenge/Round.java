package org.klaverjasaichallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.klaverjasaichallenge.score.Score;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.rank.Rank;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class Round {
	private static final int TRICK_COUNT = 8;

	private static final int AMOUNT_OF_TRICKS = 8;
	private final Map<Player, Hand> hands;
	private final List<Trick> tricksPlayed;

	private final List<Player> players;

	private final List<Suit> availableTrumps;

	private static final int MINIMUM_AMOUNT_TRUMPS_LEFT = 1;
	private static final int LEADING_PLAYER = 0;

	private static final int TEAM_1_PLAYER_1 = 0;
	private static final int TEAM_1_PLAYER_2 = 2;
	private static final int TEAM_2_PLAYER_1 = 1;
	private static final int TEAM_2_PLAYER_2 = 3;

	private Suit trump;
	private Player winner = null;

	public Round(List<Player> players) {
		this.tricksPlayed = new LinkedList<Trick>();
		this.hands = dealCards(players);
		this.availableTrumps = Card.getSuits();
		this.players = players;
	}

	public void play() {

		/**
		 * Veel plezier Action: Give cards to players
		 */
		for (Player player : players) {
			Hand playersHand = this.hands.get(player);
			player.giveCards(playersHand.getCards());
		}

		/**
		 * Action: Draw Trump
		 */
		Player playerAcceptedTrump = null;
		Suit drawnTrump = null;
		do {
			try {
				drawnTrump = this.drawTrump();
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
				
				Player player = players.get(playerIndex);
				Order playersOrder = new Order(playerIndex);
				
				boolean playOnTrump = player.playOnTrump(drawnTrump, playersOrder);
				if (playOnTrump) {
					playerAcceptedTrump = player;
					this.trump = drawnTrump;
				}
			}
		} while (playerAcceptedTrump == null);

		/**
		 * Action: Trick or treat
		 */
		for (int trickId = 0; trickId < AMOUNT_OF_TRICKS; trickId++) {
			Trick trick = new Trick();

			System.out.println("-- Start trick " + trickId + " with trump " + trump);

			for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
				Player currentPlayer = players.get(playerIndex);
				Order playersOrder = new Order(playerIndex);

				// Ask the player to return a card
				// (Trick is cloned to avoid the AI meddling with the trick data)
				Card cardPlayed = currentPlayer.getCard(trick.clone(), drawnTrump, playersOrder);

				System.out.println("--- " + currentPlayer + " played " + cardPlayed);

				// Play the card
				try {
					this.playCard(trick, currentPlayer, cardPlayed);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Score score = trick.getScore(drawnTrump);
			Player winner = trick.getWinner(drawnTrump);
			this.tricksPlayed.add(trick);
			System.out.println("--- Winner:  " + winner + " with " + score);
			
			// Notify player of end of trick
			for(Player player : players) {
				player.endOfTrick(trick);
			}
		}

		/**
		 * Action: Round ends TODO Implement this action according to the pseudo
		 * code below.
		 */

		Map<Player, Score> playerScores = this.calculateRoundScores();

		System.out.println("--- Round Scores");
		System.out.println("Team 1 Player 1 "
				+ playerScores.get(players.get(TEAM_1_PLAYER_1)));
		System.out.println("Team 1 Player 2 "
				+ playerScores.get(players.get(TEAM_1_PLAYER_2)));
		System.out.println("Team 2 Player 1 "
				+ playerScores.get(players.get(TEAM_2_PLAYER_1)));
		System.out.println("Team 2 Player 2 "
				+ playerScores.get(players.get(TEAM_2_PLAYER_2)));
	}

	/**
	 * This function creates a Deck object and gives the cards to the different
	 * players so they all have a hand of 8 cards, which is returned.
	 */
	private Map<Player, Hand> dealCards(List<Player> players) {
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

	public void playCard(Trick trick, Player player, Card card)
			throws Exception {
		if (this.hands.get(player).drawCard(card) == null) {
			throw new Exception("The player played an invalid card! This card"
					+ " is not in his hand");
		}

		// Find out current suit
		Suit leadingSuit = trick.getLeadingSuit();

		// If the leading player has already played a card (== first card)
		if (leadingSuit != null) {
			// If the player is not following suit
			if (!card.getSuit().equals(leadingSuit)) {
				// If the player cannot follow suit (!playerCanFollowSuit())
				if (!this.playerCanFollowSuit(player, leadingSuit)) {
					// If the player is playing a trump card
					if (card.getSuit().equals(this.trump)) {
						Rank highestTrumpOnTable = trick.getHighestTrump(this.trump);
						// If the player is not raising a trump, but is able
						// to (playerCanRaiseTrump()), throw exception
						if (highestTrumpOnTable != null
								&& highestTrumpOnTable.getTrumpOrder()
										.isHigherThan(
												card.getRank().getTrumpOrder())
								&& playerCanRaiseTrump(highestTrumpOnTable, player)) {
							throw new Exception(
									"Player "
											+ player
											+ " can raise the trump but is not doing it."
											+ " Current trump: " + this.trump
											+ ". Card played: " + card);
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump(), throw exception
					else if (playerHasTrump(player)) {
						throw new Exception(
								"Player "
										+ player
										+ " has trump but is not playing it. Current trump: "
										+ this.trump + ". Card played: " + card);
					}
				}
				// Else - Player can follow suit but is not, throw exception
				else {
					throw new Exception("Player " + player
							+ " can follow suit but is not. Current suit: "
							+ leadingSuit + ".  Card played: " + card);
				}
			}
		}

		trick.addCard(player, card);
	}

	private boolean playerHasTrump(Player player) {
		Hand hand = this.hands.get(player);		
		return hand.hasSuit(this.trump);
	}

	/**
	 * Checks whether an player can play a card that is higher then the currently highest ranked
	 * trump played.
	 * 
	 * @param highestTrumpOnTable
	 * @param player
	 * @return True if the player is able to, false if not
	 */
	private boolean playerCanRaiseTrump(Rank highestTrumpOnTable, Player player) {
		Hand hand = hands.get(player);
		Rank highestTrumpOfPlayer = hand.getHighestTrump(this.trump);
		
		if(highestTrumpOfPlayer != null && highestTrumpOfPlayer.getTrumpOrder().isHigherThan(highestTrumpOnTable.getTrumpOrder())) 
			return true;
		else
			return false;
	}

	/**
	 * Checks whether a player has the ability to play a card of the leading
	 * suit.
	 * 
	 * @param player
	 * @param leadingSuit
	 * @return True when the player can follow suit, false when not
	 */
	private boolean playerCanFollowSuit(Player player, Suit leadingSuit) {
		Hand hand = hands.get(player);

		if (hand.hasSuit(leadingSuit))
			return true;
		else
			return false;
	}

	/**
	 * Calculates the points players have amassed this Round
	 */
	public Map<Player, Score> calculateRoundScores() {
		
		Score team1Score = new Score(new Points(0), new Points(0));
		Score team2Score = new Score(new Points(0), new Points(0));
		
		int team1Wins = 0;
		int team2Wins = 0;

		for (int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			Trick trick = tricksPlayed.get(trickId);
			Player winner = trick.getWinner(trump);
			int winnerId = players.indexOf(winner);
			Score trickScore = trick.getScore(trump);

			// For the last trick, award extra points
			if (trickId == TRICK_COUNT - 1)
				trickScore = new Score(Points.plus(trickScore.getStockScore(),
						Score.LAST_TRICK_POINTS), trickScore.getRoemScore());

			if (winnerId == TEAM_1_PLAYER_1 || winnerId == TEAM_1_PLAYER_2) {
				team1Wins++;
				team1Score = Score.plus(team1Score, trickScore);
			}
			else {
				team2Wins++;
				team2Score = Score.plus(team2Score, trickScore);
			}
		}

		// Going wet
		if (Score.totalScorebiggerThan(team2Score,team1Score)) {
			// Winners get the roem of both teams
			team2Score = new Score(new Points(162), new Points(Points.plus(
					team2Score.getRoemScore(), team1Score.getRoemScore())));

			// The team that goes gets 0 points
			team1Score = new Score(new Points(0), new Points(0));

			System.out.println("--- Team 1 goes wet! OMG");
		}

		// Marching
		if (team1Wins == TRICK_COUNT) {
			team1Score = new Score(team1Score.getStockScore(),Points.plus(team1Score.getRoemScore(),Score.MARCH_POINTS));
			System.out.println("--- Team 1 goes marching");
		} 

		Map<Player, Score> scores = new HashMap<Player, Score>();
		scores.put(players.get(TEAM_1_PLAYER_1), team1Score);
		scores.put(players.get(TEAM_1_PLAYER_2), team1Score);
		scores.put(players.get(TEAM_2_PLAYER_1), team2Score);
		scores.put(players.get(TEAM_2_PLAYER_2), team2Score);

		return scores;
	}

	public Object getWinner() {
		return this.winner;
	}
}
