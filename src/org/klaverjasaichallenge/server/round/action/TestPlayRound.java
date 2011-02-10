package org.klaverjasaichallenge.server.round.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.suit.Suit;

public class TestPlayRound {
	private SampleRoundData testData;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		RoundData roundData = this.testData.getRoundData();
		roundData.setTrump(mock(Suit.class));
		roundData.setTrumpPlayer(roundData.getActivePlayer());

		new InformPlayersRuleSet(roundData).execute();
		new DealCards(roundData).execute();

		RoundAction playRound = new PlayRound(roundData);
		playRound.execute();
	}

	@Test
	public void verifyGetCard() {
		for(Player player : this.testData.getPlayers()) {
			verify(player, times(Trick.COUNT)).getCard(any(Trick.class),
					any(Order.class));
		}
	}

	@Test
	public void verifyEndOfTrick() {
		for(Player player : this.testData.getPlayers()) {
			verify(player, times(Trick.COUNT)).endOfTrick(any(Trick.class));
		}
	}

	@Test
	public void verifyPlayedTricks() {
		RoundData roundData = this.testData.getRoundData();
		assertEquals(roundData.getTricksPlayed().size(), Trick.COUNT);
	}

}
