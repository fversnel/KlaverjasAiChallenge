package org.klaverjasaichallenge.engine.round.action.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestPlayRound {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(this.testData.getPlayerOne(), Suit.CLUBS);

		new InformPlayersRuleSet(roundData).execute();
		new DealCards(roundData).execute();

		RoundAction playRound = new PlayRound(roundData);
		playRound.execute();
	}

	@Test
	public void verifyGetCard() {
		for(final Player player : this.testData.getTable()) {
			verify(player, times(Trick.COUNT)).getCard(any(Trick.class));
		}
	}

	@Test
	public void verifyEndOfTrick() {
		for(final Player player : this.testData.getTable()) {
			verify(player, times(Trick.COUNT)).endOfTrick(any(Trick.class));
		}
	}

	@Test
	public void verifyPlayedTricks() {
		RoundData roundData = this.testData.getRoundData();
		assertEquals(roundData.getTricksPlayed().size(), Trick.COUNT);
	}

}
