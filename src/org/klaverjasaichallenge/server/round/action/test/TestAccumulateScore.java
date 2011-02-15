package org.klaverjasaichallenge.server.round.action.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;
import org.klaverjasaichallenge.server.round.action.*;

/**
 * TODO Implement tests for accumulating scores.
 */
public class TestAccumulateScore {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(mock(Suit.class));
		roundData.setTrumpPlayer(this.testData.getPlayerOne());

		new InformPlayersRuleSet(roundData).execute();
		new DealCards(roundData).execute();
		new PlayRound(roundData).execute();

		RoundAction accumulateScore = new AccumulateScore(roundData);
		accumulateScore.execute();
	}

	/**
	 * TODO Implement test.
	 */
	@Test
	public void testMarching() {

	}

}
