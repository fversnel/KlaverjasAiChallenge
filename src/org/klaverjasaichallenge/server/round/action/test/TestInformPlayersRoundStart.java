package org.klaverjasaichallenge.server.round.action.test;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.server.round.action.*;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class TestInformPlayersRoundStart {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(this.testData.getPlayerOne(), mock(Suit.class));

		RoundAction informPlayersRoundStart = new InformPlayersRoundStart(roundData);
		informPlayersRoundStart.execute();
	}

	@Test
	public void verifyStartOfRoundIndication() {
		for(final Player player : this.testData.getTable()) {
			verify(player).startOfRound(anyInt(), any(Suit.class),
					anyInt(), anyInt(), anyInt(), anyInt());
		}
	}
}
