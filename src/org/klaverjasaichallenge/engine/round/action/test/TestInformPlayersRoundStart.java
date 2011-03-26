package org.klaverjasaichallenge.engine.round.action.test;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestInformPlayersRoundStart {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(this.testData.getPlayerOne(), Suit.SPADES);

		RoundAction informPlayersRoundStart = new InformPlayersRoundStart(roundData);
		informPlayersRoundStart.execute();
	}

	@Test
	public void verifyStartOfRoundIndication() {
		for(final Player player : this.testData.getTable()) {
			verify(player).notifyStartOfRound(anyInt(), 
				eq(this.testData.getRoundData().getTrump()),
				anyInt(), anyInt(), anyInt(), anyInt());
		}
	}

}
