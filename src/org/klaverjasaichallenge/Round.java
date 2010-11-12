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

import org.klaverjasaichallenge.shared.CheatException;

/**
 * TODO Refactor this class since it's responsibility seems to be to big. Look
 * at the amount of named constants for example... way too much.
 */
public class Round {
	private static final int TRICK_COUNT = 8;

	private static final int AMOUNT_OF_TRICKS = 8;
	private final Map<Player, Hand> hands;
	private final List<Trick> tricksPlayed;

	private Table table;

	private final List<Suit> availableTrumps;
	
	private Player acceptedTrump;
	
	private Map<Team, Score> roundScores;

	private static final int MINIMUM_AMOUNT_TRUMPS_LEFT = 1;
//	private static final int LEADING_PLAYER = 0;
//
//	private static final int TEAM_1_PLAYER_1 = 0;
//	private static final int TEAM_1_PLAYER_2 = 2;
//	private static final int TEAM_2_PLAYER_1 = 1;
//	private static final int TEAM_2_PLAYER_2 = 3;

	private Suit trump;
	private Player winner = null;

	public Round(Table table) {
		this.tricksPlayed = new LinkedList<Trick>();
		this.hands = dealCards(table);
		this.availableTrumps = Card.getSuits();
		this.table = table;
		this.acceptedTrump = null;
		this.roundScores = null;
	}

	public void play() {

		/**
		 * Veel plezier Action: Give cards to players
		 * TODO Why is dealing cards and giving cards to players a seperate
		 * action?
		 */
		for (Player player : table.getPlayers()) {
			Hand playersHand = this.hands.get(player);
			player.giveCards(playersHand.getCards());
		}

		/**
		 * Action: Draw Trump
		 */
		Suit drawnTrump = null;
		do {
			try {
				drawnTrump = this.drawTrump();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// TODO Force gaan na 3x
			Table trumpTable = this.table;
			for (int playerIndex = 0; playerIndex < 4; playerIndex++) {

				Player player = trumpTable.getActivePlayer();
				
				// TODO Request order from table instead 
				Order playersOrder = new Order(playerIndex);

				boolean playOnTrump = player.playOnTrump(drawnTrump, playersOrder);
				if (playOnTrump) {
					this.acceptedTrump = player;
					this.trump = drawnTrump;
					System.out.println(this.acceptedTrump + " goes on " + drawnTrump);
					break;
				}
				
				trumpTable = trumpTable.nextPlayer();
			}
		} while (this.acceptedTrump == null);

		/**
		 * Action: Play all tricks, when the last trick is player, the round
		 * will be ended.
		 */
		for (int trickId = 0; trickId < AMOUNT_OF_TRICKS; trickId++) {
			Trick trick = new Trick();

			System.out.println("-- Start trick " + trickId + " with trump " + trump);

			for (int playerIndex = 0; playerIndex < 4; playerIndex++) {
				Player currentPlayer = table.getActivePlayer();
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
				
				this.table = this.table.nextPlayer();
			}

			Score score = trick.getScore(drawnTrump);
			Player winner = trick.getWinner(drawnTrump);
			this.tricksPlayed.add(trick);
			System.out.println("--- Winner:  " + winner + " with " + score);

			// Notify player of end of trick
			for(Player player : table.getPlayers()) {
				player.endOfTrick(trick);
			}
			
			assert(drawnTrump == this.trump);
			
			this.table = this.table.nextTrick(winner);
		}

		/**
		 * Action: Round ends
		 */

		this.roundScores = this.calculateRoundScores();

		System.out.println("--- Round Scores");
		for(Team team : roundScores.keySet()) {
			System.out.println(team + " scores: " + roundScores.get(team) + " points");
		}
	}
	
	public Score getScore(Team team) {
		return this.roundScores.get(team);
	}

	/**
	 * This function creates a Deck object and gives the cards to the different
	 * players so they all have a hand of 8 cards, which is returned.
	 */
	private Map<Player, Hand> dealCards(Table table) {
		Deck deck = new Deck();
		Map<Player, Hand> hands = new HashMap<Player, Hand>();

		for (Player player : table.getPlayers()) {
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
			throw new CheatException("The player played an invalid card! This card"
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
							throw new CheatException( "Player " + player
											+ " can raise the trump but is not doing it."
											+ " Current trump: " + this.trump
											+ ". Card played: " + card);
						}
					}
					// Else - Player is not playing a trump card, but is able
					// to (playerHasTrump(), throw exception
					else if (playerHasTrump(player)) {
						throw new CheatException( "Player " + player
										+ " has trump but is not playing it. Current trump: "
										+ this.trump + ". Card played: " + card);
					}
				}
				// Else - Player can follow suit but is not, throw exception
				else {
					throw new CheatException("Player " + player
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
		boolean result = false;

		Hand hand = hands.get(player);
		if (hand.hasSuit(leadingSuit)) {
			result = true;
		}

		return result;
	}

	/**
	 * Calculates the points players have amassed this Round
	 */
	private Map<Team, Score> calculateRoundScores() {

		Team teamOffensive = table.getTeamFromPlayer(this.acceptedTrump);
		Team teamDefensive = table.getOtherTeam(acceptedTrump);
		Map<Team, Score> teamScores = new HashMap<Team, Score>();
		for(Team team: this.table.getTeams()) {
			teamScores.put(team, new Score());
		}
		
		for (int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			Trick trick = tricksPlayed.get(trickId);
			Player winner = trick.getWinner(trump);
			Score trickScore = trick.getScore(trump);
			Team winningTeam = this.table.getTeamFromPlayer(winner);

			// For the last trick, award extra points
			if (trickId == TRICK_COUNT - 1)
				trickScore = new Score(Points.plus(trickScore.getStockScore(),
						Score.LAST_TRICK_POINTS), trickScore.getRoemScore());
			
			Score previousScore = teamScores.get(winningTeam);
			teamScores.put(winningTeam, Score.plus(previousScore, trickScore));
		}

		// Going wet
		if (Score.totalScoreBiggerThanOrEquals(teamScores.get(teamDefensive),teamScores.get(teamOffensive))) {
			// Winners get the roem of both teams
			
			Score defensiveScore = new Score(new Points(162), new Points(Points.plus(
					teamScores.get(teamDefensive).getRoemScore(), 
					teamScores.get(teamOffensive).getRoemScore())));			
			teamScores.put(teamDefensive, defensiveScore);

			// The team that goes gets 0 points
			teamScores.put(teamOffensive, new Score(new Points(0), new Points(0)));

			System.out.println("--- " + teamOffensive + " goes wet! OMG");
		}

		// Marching
	
		if (teamScores.get(teamOffensive).getStockScore().getPoints() == 162) {			
			Score newScore = new Score(teamScores.get(teamOffensive).getStockScore(),Points.plus(teamScores.get(teamOffensive).getRoemScore(),Score.MARCH_POINTS));
			teamScores.put(teamOffensive, newScore);
			System.out.println("--- " + teamOffensive + " goes marching");
			
		}

		return teamScores;
	}

	public Object getWinner() {
		return this.winner;
	}
}
