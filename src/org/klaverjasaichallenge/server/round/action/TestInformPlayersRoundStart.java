package org.klaverjasaichallenge.server.round.action;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class TestInformPlayersRoundStart {
	private SampleRoundData
	testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(mock(Suit.class));
		roundData.setTrumpPlayer(this.testData.getPlayerOne());
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
