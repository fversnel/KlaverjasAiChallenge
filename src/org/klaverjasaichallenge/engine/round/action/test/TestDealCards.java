package org.klaverjasaichallenge.engine.round.action.test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.*;

import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.shared.Player;

/**
 *
 * @author Frank Versnel
 */
public class TestDealCards {
	private RoundData roundData;

	private RoundAction dealCardsAction;

	@Before
	public void setUp() {
		this.roundData = new SampleRoundData().getRoundData();

		this.dealCardsAction = new DealCards(this.roundData);
		this.dealCardsAction.execute();
	}

	@Test
	public void testHandsExists() {
		for(final Player player : this.roundData.getTable()) {
			assertNotNull(this.roundData.getPlayersHand(player));
		}
	}

	@Test
	public void verifyGiveCards() {
		for(final Player player : this.roundData.getTable()) {
			verify(player).giveCards(any(List.class));
		}
	}

}
