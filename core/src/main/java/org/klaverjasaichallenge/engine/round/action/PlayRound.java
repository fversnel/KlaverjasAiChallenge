package org.klaverjasaichallenge.engine.round.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.CheatException;
import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.engine.round.data.CardsDealt;
import org.klaverjasaichallenge.engine.round.data.PlayedTricks;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class PlayRound implements RoundAction<PlayedTricks> {
	public static final int TOTAL_NUMBER_OF_TRICKS = 8;

	private static final Logger logger = LoggerFactory.getLogger(PlayRound.class);

	private final RuleSet ruleSet;
	private final Table table;
	private final CardsDealt cardsDealt;

	private final Suit trump;

	public PlayRound(final RuleSet ruleSet, final Table table,
			final TrumpPlayer trumpPlayer, final CardsDealt cardsDealt) {
		this.ruleSet = ruleSet;
		this.table = table;
		this.cardsDealt = cardsDealt;

		this.trump = trumpPlayer.getTrump();
	}

	@Override
	public PlayedTricks execute() {
		final PlayedTricks result = new PlayedTricks();

		for (int trickId = 1; trickId <= TOTAL_NUMBER_OF_TRICKS; trickId++) {
			EngineTrick playedTrick = this.playTrick(trickId);
			result.addPlayedTrick(playedTrick);

			this.notifyPlayersEndTrick(playedTrick);

			final Player winner = playedTrick.getWinner();
			this.table.nextTrick(winner);
			logger.info("Winner: {} with {}", winner, new Score(playedTrick));
		}

		return result;
	}

	private EngineTrick playTrick(final int trickId) {
		final boolean isLastTrick = (trickId == TOTAL_NUMBER_OF_TRICKS);
		EngineTrick trick = new EngineTrick(this.trump, isLastTrick);

		logger.info("Starting trick {} with trump {}", trickId, trick.getTrump());

		for(final Player currentPlayer : this.table) {
			this.playCard(trick, currentPlayer);
		}

		return trick;
	}

	/**
	 * Checks if the card that a player returns is indeed
	 * a valid card to play within this trick.
	 *
	 * @throws CheatException thrown when the player cheats.
	 */
	private void playCard(final EngineTrick trick, final Player player) {
		final Card cardPlayed = player.playCard(trick);
		final Hand playersHand = this.cardsDealt.get(player);

		final boolean cardIsLegal = this.ruleSet.isLegalCard(trick.clone(), cardPlayed, playersHand);
		final boolean cardIsInHand = playersHand.drawCard(cardPlayed);

		if(cardIsLegal && cardIsInHand) {
			trick.addCard(player, cardPlayed);
			logger.info("{} played {}", player, cardPlayed);
		} else {
			throw new CheatException(String.format("Player %s cheated with %s and hand %s",
					player, cardPlayed, playersHand));
		}
	}

	private void notifyPlayersEndTrick(final EngineTrick trick) {
		for (final Player player : this.table) {
			player.notifyEndOfTrick(trick.clone());
		}
	}

}
