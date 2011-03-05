package org.klaverjasaichallenge.engine.round.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.Trick;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Rank;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestTrick {
	private Player testPlayer;
	private Trick testTrick;

	@Before
	public void setUp() {
		this.testPlayer = mock(Player.class);
		this.testTrick = new Trick(Suit.HEARTS, false);
	}

	@Test
	public void testGetCardFromPlayer() {
		Card expected = Card.SEVEN_OF_HEARTS;
		this.testTrick.addCard(this.testPlayer, expected);

		Card actual =
			this.testTrick.getCardFromPlayer(this.testPlayer.hashCode());

		assertTrue(expected.equals(actual));
	}

}
