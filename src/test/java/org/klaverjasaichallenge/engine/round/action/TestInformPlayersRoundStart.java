package org.klaverjasaichallenge.engine.round.action;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.engine.round.data.TrumpPlayer;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestInformPlayersRoundStart {
	private SampleRoundData testData;

	private Suit trump;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		this.trump = Suit.CLUBS;
		TrumpPlayer trumpPlayer = new TrumpPlayer(this.testData.getPlayerOne(), this.trump);
		new InformPlayersRoundStart(this.testData.getTable(), trumpPlayer).execute();
	}

	@Test
	public void verifyStartOfRoundIndication() {
		for(final Player player : this.testData.getTable()) {
			verify(player).notifyStartOfRound(anyInt(),
				eq(this.trump),
				anyInt(), anyInt(), anyInt(), anyInt());
		}
	}

}
