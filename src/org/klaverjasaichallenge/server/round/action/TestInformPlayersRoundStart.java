package org.klaverjasaichallenge.server.round.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class TestInformPlayersRoundStart {
	private SampleRoundData testData;

	private RoundAction informPlayersRoundStart;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(mock(Suit.class));
		roundData.setTrumpPlayer(roundData.getActivePlayer());
		RoundAction informPlayersRoundStart = new InformPlayersRoundStart(roundData);
		informPlayersRoundStart.execute();
	}

	@Test
	public void verifyStartOfRoundIndication() {
		for(Player player : this.testData.getPlayers()) {
			verify(player).startOfRound(anyInt(), any(Suit.class),
					anyInt(), anyInt(), anyInt(), anyInt());
		}
	}
}
