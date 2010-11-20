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
import org.klaverjasaichallenge.shared.card.suit.Suit;


/**
 * TODO Refactor this class since it's responsibility seems to be to big.
 */
public class Round {
	private static final int TRICK_COUNT = 8;
	private static final int FORCED_PLAY_ON_TRUMP = 3;
	private static final int PLAYER_COUNT = 4;

	private Table table;
	private final RuleSet ruleSet;
	private Map<Team, Score> roundScores;
	private Player playerAcceptedTrump;

	private final Logger logger;

	public Round(final Table table, final RuleSet ruleSet) {
		this.table = table;
		this.ruleSet = ruleSet;
		this.roundScores = this.initializeRoundScores(this.table.getTeams());
		this.playerAcceptedTrump = null;

		this.logger = Logger.getLogger("KlaverjasLogger");
	}

	public void play() {
		final Map<Player, Hand> hands = dealCards(this.table.getPlayers());
		final Suit trump = this.drawTrump();
		this.informPlayersStartOfRound(trump);

		/**
		 * Action: Play all tricks, when the last trick is player, the round
		 * will be ended.
		 */
		final List<Trick> tricksPlayed = new LinkedList<Trick>();
		for (int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			final Trick trick = new Trick();

			this.logger.debug("-- Start trick " + trickId + " with trump " + trump);

			for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
				final Player currentPlayer = this.table.getActivePlayer();
				final Order playersOrder = new Order(playerIndex);

				// Ask the player to return a card
				// (Trick is cloned to avoid the AI meddling with the trick
				// data)
				final Card cardPlayed = currentPlayer.getCard(trick.clone(), playersOrder);

				this.logger.debug("--- " + currentPlayer + " played " + cardPlayed);

				// Check if the card is valid
				try {
					this.playCard(trick, currentPlayer, cardPlayed, hands, trump);
				} catch (Exception e) {
					// TODO Do not print an error here, but punish!
					e.printStackTrace();
				}

				this.table = this.table.nextPlayer();
			}

			final Score score = trick.getScore(trump);
			final Player winner = trick.getWinner(trump);
			tricksPlayed.add(trick);
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
		this.roundScores = this.calculateRoundScores(tricksPlayed, trump);

		this.logger.debug("--- Round Scores");
		for (Team team : this.roundScores.keySet()) {
			this.logger.debug(team + " scores: " + this.roundScores.get(team) + " points");
		}

	}

	public Score getScore(final Team team) {
		return this.roundScores.get(team);
	}

	/**
	 * This function creates a Deck object and gives the cards to the different
	 * players so they all have a hand of 8 cards, which is returned.
	 */
	private Map<Player, Hand> dealCards(final List<Player> players) {
		assert(players.size() == PLAYER_COUNT);

		final Deck deck = new Deck();
		final Map<Player, Hand> hands = new HashMap<Player, Hand>();

		for(final Player player : players) {
			final Hand playersHand = new Hand(deck);

			// Serverside representation of the hand
			hands.put(player, playersHand);

			// Notify Player/AI of the hand
			player.giveCards(playersHand.getCards());
		}

		return hands;
	}

	/**
	 * Drawing trump is the process of choosing which player plays and on which
	 * trump.
	 *
	 * Drawing trump is the subprocedure of deciding who plays on which trump. At
	 * the end of this function the object variables this.trump and
	 * this.playerAcceptedTrump will be set.
	 *
	 * TODO See if we can make this.trump and playerAcceptedTrump local
	 * variables that only exist in play() and subprocedures of play. Maybe let
	 * this function return an object that contains them both.
	 */
	private Suit drawTrump() {
		Suit drawnTrump = null;
		int amountTrumpsDrawn = 0;
		final List<Suit> availableTrumps = Card.getSuits();
		do {
			drawnTrump = this.getRandomTrump(availableTrumps);
			availableTrumps.remove(drawnTrump);

			amountTrumpsDrawn++;

			Table trumpTable = this.table;
			for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
				final Player player = trumpTable.getActivePlayer();

				// When the player decides he want to play on this trump
				// After three tries force active player to go on this third trump
				if (player.playOnTrump(drawnTrump, new Order(playerIndex)) ||
						amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
					this.playerAcceptedTrump = player;

					if(amountTrumpsDrawn == FORCED_PLAY_ON_TRUMP) {
						this.logger.debug(this.playerAcceptedTrump + " is " +
								"forced to go on" + drawnTrump);
					} else {
						this.logger.debug(this.playerAcceptedTrump + " goes on " +
								drawnTrump);
					}

					break;
				}

				trumpTable = trumpTable.nextPlayer();
			}

		} while (this.playerAcceptedTrump == null);

		// Asserts that this function always results in a filled
		// playerAcceptedTrump and trump
		assert (this.playerAcceptedTrump != null);
		assert (drawnTrump != null);

		return drawnTrump;
	}

	private Suit getRandomTrump(final List<Suit> availableTrumps) {
		final Random random = new Random(System.nanoTime());
		final int trumpIndex = random.nextInt(availableTrumps.size());
		return availableTrumps.get(trumpIndex);
	}

	private void informPlayersStartOfRound(final Suit trump) {
		Table roundTable = this.table;
		final int leadingPlayer = this.playerAcceptedTrump.hashCode();
		for(int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
			final Player currentPlayer = roundTable.getActivePlayer();

			final Team playerTeam = roundTable.getTeamFromPlayer(currentPlayer);
			final Team otherTeam = roundTable.getOtherTeam(currentPlayer);
			final int currentPlayerId = currentPlayer.hashCode();
			final int teamMateId = playerTeam.getOtherPlayer(currentPlayer).hashCode();
			final int enemy1Id = otherTeam.getFirstPlayer().hashCode();
			final int enemy2Id = otherTeam.getSecondPlayer().hashCode();

			currentPlayer.startOfRound(leadingPlayer, trump,
					currentPlayerId, teamMateId, enemy1Id, enemy2Id);

			roundTable = roundTable.nextPlayer();
		}
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
			final Card card, final Map<Player, Hand> hands, final Suit trump) throws CheatException {
		if (hands.get(player).drawCard(card) == null) {
			throw new CheatException("Player " + player + " played an invalid " +
					" card. The card (" + card + ") is not in his hand");
		}

		this.ruleSet.checkCardLegitimacy(trick, card, player, hands.get(player), trump);
		trick.addCard(player, card);
	}

	/**
	 * Calculates the points players have amassed this Round
	 *
	 * @uses roundScores
	 */
	private Map<Team, Score> calculateRoundScores(final List<Trick> tricks, final Suit trump) {
		assert(tricks.size() == TRICK_COUNT);

		final Team teamOffensive = this.table.getTeamFromPlayer(this.playerAcceptedTrump);
		final Team teamDefensive = this.table.getOtherTeam(this.playerAcceptedTrump);
		final Map<Team, Score> roundScores = this.initializeRoundScores(this.table.getTeams());

		for(int trickId = 0; trickId < TRICK_COUNT; trickId++) {
			final Trick trick = tricks.get(trickId);
			final Player winner = trick.getWinner(trump);
			final Team winningTeam = this.table.getTeamFromPlayer(winner);
			Score trickScore = trick.getScore(trump);

			// For the last trick, award extra points
			if(trickId == TRICK_COUNT - 1) {
				trickScore = new Score(Points.plus(trickScore.getStockScore(), Score.LAST_TRICK_POINTS), trickScore
						.getRoemScore());
			}

			final Score previousScore = roundScores.get(winningTeam);
			roundScores.put(winningTeam, Score.plus(previousScore, trickScore));
		}

		// Going wet
		if(Score
				.totalScoreBiggerThanOrEquals(roundScores.get(teamDefensive), roundScores.get(teamOffensive))) {
			// Winners get the roem of both teams

			final Score defensiveScore = new Score(new Points(162), new Points(Points.plus(roundScores
					.get(teamDefensive).getRoemScore(), roundScores.get(teamOffensive).getRoemScore())));
			roundScores.put(teamDefensive, defensiveScore);

			// The team that goes gets 0 points
			roundScores.put(teamOffensive, new Score(new Points(0), new Points(0)));

			this.logger.debug("--- " + teamOffensive + " goes wet! OMG");
		}

		// Marching
		if (roundScores.get(teamOffensive).getStockScore().getPoints() == 162) {
			final Score newScore = new Score(roundScores.get(teamOffensive).getStockScore(), Points.plus(
					roundScores.get(teamOffensive).getRoemScore(), Score.MARCH_POINTS));
			roundScores.put(teamOffensive, newScore);
			this.logger.debug("--- " + teamOffensive + " goes marching");

		}

		return roundScores;
	}

	private Map<Team, Score> initializeRoundScores(final List<Team> teams) {
		final Map<Team, Score> roundScores = new HashMap<Team, Score>();
		for (final Team team : teams) {
			roundScores.put(team, new Score());
		}

		return roundScores;
	}

}
