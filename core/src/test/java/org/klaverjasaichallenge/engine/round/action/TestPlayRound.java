package org.klaverjasaichallenge.engine.round.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.data.CardsDealt;
import org.klaverjasaichallenge.engine.round.data.PlayedTricks;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestPlayRound {
	private SampleRoundData testData;
	private PlayedTricks roundResult;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		new InformPlayersRuleSet(this.testData.getRotterdamRuleSet(), this.testData.getTable()).execute();
		final CardsDealt cardsDealt = new DealCards(this.testData.getTable()).execute();
		final TrumpPlayer trumpPlayer = new TrumpPlayer(this.testData.getPlayerThree(), Suit.HEARTS);

		this.roundResult = new PlayRound(this.testData.getRotterdamRuleSet(), this.testData.getTable(), trumpPlayer, cardsDealt).execute();
	}

	@Test
	public void verifyGetCard() {
		for(final Player player : this.testData.getTable()) {
			verify(player, times(PlayRound.TOTAL_NUMBER_OF_TRICKS)).playCard(any(Trick.class));
		}
	}

	@Test
	public void verifyEndOfTrick() {
		for(final Player player : this.testData.getTable()) {
			verify(player, times(PlayRound.TOTAL_NUMBER_OF_TRICKS)).notifyEndOfTrick(any(Trick.class));
		}
	}

	@Test
	public void verifyPlayedTricks() {
		assertEquals(PlayRound.TOTAL_NUMBER_OF_TRICKS, this.roundResult.getTricksPlayed().size());
	}

}
