package org.klaverjasaichallenge;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

// Import log4j classes.
import org.apache.log4j.Logger;

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
	private static final int FORCED_PLAY_ON_TRUMP = 3;

	private Table table;

	private final Map<Team, Score> roundScores;
	private final List<Trick> tricksPlayed;
	private final List<Suit> availableTrumps;

	private Map<Player, Hand> hands;

	private Player playerAcceptedTrump;
	private Suit trump;
	private Player winner;

	private RuleSet ruleSet;

	private Logger logger;

	public Round(final Table table, final RuleSet ruleSet) {
		this.table = table;

		this.availableTrumps = Card.getSuits();
		this.roundScores = new HashMap<Team, Score>();
		this.tricksPlayed = new LinkedList<Trick>();
		this.ruleSet = ruleSet;

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	public void play() {

		// Deal the cards and notify the players
		this.hands = dealCards(this.table);

		// Draw Trump is the process of choosing which player plays and on which
		// trump
		drawTrump();

		/**
		 * Action: Play all tricks, when the last trick is player, the round
		 * will be ended.
		 */
		for (int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			Trick trick = new Trick();

			this.logger.debug("-- Start trick " + trickId + " with trump " + this.trump);

			for (int playerIndex = 0; playerIndex < 4; playerIndex++) {
				Player currentPlayer = this.table.getActivePlayer();
				Order playersOrder = new Order(playerIndex);

				// Ask the player to return a card
				// (Trick is cloned to avoid the AI meddling with the trick
				// data)
				Card cardPlayed = currentPlayer.getCard(trick.clone(), this.trump, playersOrder);

				this.logger.debug("--- " + currentPlayer + " played " + cardPlayed);

				// Check if the card is valid
				try {
					this.playCard(trick, currentPlayer, cardPlayed);
				} catch (Exception e) {
					// TODO Do not print an error here, but punish!
					e.printStackTrace();
				}

				this.table = this.table.nextPlayer();
			}

			Score score = trick.getScore(this.trump);
			Player winner = trick.getWinner(this.trump);
			this.tricksPlayed.add(trick);
			this.logger.debug("--- Winner:  " + winner + " with " + score);

			// Notify player of end of trick
			for (Player player : this.table.getPlayers()) {
				player.endOfTrick(trick);
			}

			this.table = this.table.nextTrick(winner);
		}

		/**
		 * Action: Round ends
		 */
		this.calculateRoundScores();

		this.logger.debug("--- Round Scores");
		for (Team team : this.roundScores.keySet()) {
			this.logger.debug(team + " scores: " + this.roundScores.get(team) + " points");
		}
	}

	public Object getWinner() {
		return this.winner;
	}

	public Score getScore(Team team) {
		return this.roundScores.get(team);
	}

	/**
	 * DrawTrump is the subprocedure of deciding who plays on which trump. At
	 * the end of this function the object variables this.trump and
	 * this.playerAcceptedTrump will be set.
	 *
	 * TODO See if we can make this.trump and playerAcceptedTrump local
	 * variables that only exist in play() and subprocedures of play. Maybe let
	 * this function return an object that contains them both.
	 */
	private void drawTrump() {
		Suit drawnTrump = null;
		int amountTrumpsDrawn = 0;
		do {

			drawnTrump = this.getAvailableTrump();
			amountTrumpsDrawn++;

			Table trumpTable = this.table;
			for (int playerIndex = 0; playerIndex < 4; playerIndex++) {

				Player player = trumpTable.getActivePlayer();

				// Force active player to go on this third trump
				if (amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
					this.playerAcceptedTrump = player;
					this.trump = drawnTrump;
					this.logger.debug(this.playerAcceptedTrump + " is forced to go on " + drawnTrump);
					break;
				}

				// When the player decides he want to play on this trump
				if (player.playOnTrump(drawnTrump, new Order(playerIndex))) {
					this.playerAcceptedTrump = player;
					this.trump = drawnTrump;
					this.logger.debug(this.playerAcceptedTrump + " goes on " + drawnTrump);
					break;
				}

				trumpTable = trumpTable.nextPlayer();
			}

		} while (this.playerAcceptedTrump == null);

		// Asserts that this function always results in a filled
		// playerAcceptedTrump and trump
		assert (this.playerAcceptedTrump != null);
		assert (this.trump != null);
	}


	private Suit getAvailableTrump() {
		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(this.availableTrumps.size());
		final Suit chosenTrump = this.availableTrumps.remove(trumpIndex);

		this.trump = chosenTrump;

		return chosenTrump;
	}

	/**
	 * This function creates a Deck object and gives the cards to the different
	 * players so they all have a hand of 8 cards, which is returned.
	 */
	private Map<Player, Hand> dealCards(Table table) {
		Deck deck = new Deck();
		Map<Player, Hand> hands = new HashMap<Player, Hand>();

		for (Player player : table.getPlayers()) {
			Hand playersHand = new Hand(deck);

			// Serverside representation of the hand
			hands.put(player, playersHand);

			// Notify Player/AI of the hand
			player.giveCards(playersHand.getCards());
		}

		return hands;
	}

	/**
	 * Playcard checks if the card that a player returns is indeed
	 * a valid card to play within this trick.
	 *
	 * @param trick
	 * @param player
	 * @param card
	 * @throws Exception
	 */
	private void playCard(final Trick trick, final Player player, 
			final Card card) throws CheatException { 
		if (this.hands.get(player).drawCard(card) == null) {
			throw new CheatException("Player " + player + " played an invalid "
					" card. The card (" + card + ") is not in his hand");
		}

		this.ruleSet.checkCardLegitimacy(trick, card, player, this.hands.get(player), trump);
		trick.addCard(player, card);
	}

	/**
	 * Calculates the points players have amassed this Round
	 *
	 * @uses roundScores
	 */
	private void calculateRoundScores() {
		Team teamOffensive = this.table.getTeamFromPlayer(this.playerAcceptedTrump);
		Team teamDefensive = this.table.getOtherTeam(this.playerAcceptedTrump);
		for (Team team : this.table.getTeams()) {
			this.roundScores.put(team, new Score());
		}

		for (int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			Trick trick = this.tricksPlayed.get(trickId);
			Player winner = trick.getWinner(this.trump);
			Score trickScore = trick.getScore(this.trump);
			Team winningTeam = this.table.getTeamFromPlayer(winner);

			// For the last trick, award extra points
			if (trickId == TRICK_COUNT - 1)
				trickScore = new Score(Points.plus(trickScore.getStockScore(), Score.LAST_TRICK_POINTS), trickScore
						.getRoemScore());

			Score previousScore = this.roundScores.get(winningTeam);
			this.roundScores.put(winningTeam, Score.plus(previousScore, trickScore));
		}

		// Going wet
		if (Score
				.totalScoreBiggerThanOrEquals(this.roundScores.get(teamDefensive), this.roundScores.get(teamOffensive))) {
			// Winners get the roem of both teams

			Score defensiveScore = new Score(new Points(162), new Points(Points.plus(this.roundScores
					.get(teamDefensive).getRoemScore(), this.roundScores.get(teamOffensive).getRoemScore())));
			this.roundScores.put(teamDefensive, defensiveScore);

			// The team that goes gets 0 points
			this.roundScores.put(teamOffensive, new Score(new Points(0), new Points(0)));

			this.logger.debug("--- " + teamOffensive + " goes wet! OMG");
		}

		// Marching

		if (this.roundScores.get(teamOffensive).getStockScore().getPoints() == 162) {
			Score newScore = new Score(this.roundScores.get(teamOffensive).getStockScore(), Points.plus(
					this.roundScores.get(teamOffensive).getRoemScore(), Score.MARCH_POINTS));
			this.roundScores.put(teamOffensive, newScore);
			this.logger.debug("--- " + teamOffensive + " goes marching");

		}
	}
}
