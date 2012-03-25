package org.klaverjasaichallenge.engine.round.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.klaverjasaichallenge.engine.Table;
import org.klaverjasaichallenge.engine.round.data.CardsDealt;
import org.klaverjasaichallenge.shared.Deck;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;

/**
 *
 * @author Joost Pastoor
 * @author Frank Versnel
 */
public class DealCards implements RoundAction<CardsDealt> {
	private static final Logger logger = LoggerFactory.getLogger(DealCards.class);

	private Table table;

	public DealCards(final Table table) {
		this.table = table;
	}

	@Override
	public CardsDealt execute() {
		final Deck deck = new Deck();
		logger.debug("Cards in deck: {}", deck.getCards());
		final CardsDealt cardsDealt = new CardsDealt();

		for(final Player currentPlayer : this.table) {
			final Hand dealtHand = this.dealHand(deck, currentPlayer);

			cardsDealt.put(currentPlayer, dealtHand);
		}

		assert(deck.isEmpty());

		return cardsDealt;
	}

	private Hand dealHand(final Deck deck, final Player player) {
		final Hand playersHand = new Hand(deck);

		player.receiveCards(playersHand.clone());
		logger.info("{} gets cards: {}", player, playersHand);

		return playersHand;
	}

}
