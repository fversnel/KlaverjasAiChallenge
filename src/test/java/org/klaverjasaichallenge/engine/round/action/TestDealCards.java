package org.klaverjasaichallenge.engine.round.action;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.*;

import org.klaverjasaichallenge.engine.round.action.*;
import org.klaverjasaichallenge.engine.round.data.CardsDealt;
import org.klaverjasaichallenge.shared.Hand;
import org.klaverjasaichallenge.shared.Player;

/**
 *
 * @author Frank Versnel
 */
public class TestDealCards {
	private SampleRoundData testData;

	private CardsDealt cardsDealt;

	@Before
	public void setUp() {
		this.testData = new SampleRoundData();

		this.cardsDealt = new DealCards(this.testData.getTable()).execute();
	}

	@Test
	public void testHandsExists() {
		for(final Player player : this.testData.getTable()) {
			assertNotNull(this.cardsDealt.get(player));
		}
	}

	@Test
	public void verifyGiveCards() {
		for(final Player player : this.testData.getTable()) {
			verify(player).receiveCards(any(Hand.class));
		}
	}

}
