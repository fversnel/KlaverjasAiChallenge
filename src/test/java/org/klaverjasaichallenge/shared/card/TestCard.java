package org.klaverjasaichallenge.shared.card;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestCard {

	@Test
	public void testMax() {
		List<Card> cardsToTest = new ArrayList<Card>();
		Trick trick = this.createMockTrick(Suit.CLUBS, Suit.HEARTS);

		final Card expected = Card.JACK_OF_HEARTS;

		cardsToTest.add(Card.ACE_OF_CLUBS);
		cardsToTest.add(expected);
		cardsToTest.add(Card.JACK_OF_DIAMONDS);
		cardsToTest.add(Card.TEN_OF_HEARTS);

		final Card actual = Card.max(trick, cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testMax2() {
		List<Card> cardsToTest = new ArrayList<Card>();
		Trick trick = createMockTrick(Suit.SPADES, Suit.CLUBS);

		final Card expected = Card.SEVEN_OF_CLUBS;

		cardsToTest.add(Card.JACK_OF_SPADES);
		cardsToTest.add(Card.QUEEN_OF_SPADES);
		cardsToTest.add(Card.KING_OF_SPADES);
		cardsToTest.add(expected);

		final Card actual = Card.max(trick,	cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testHigherThan() {
		final Card card1 = Card.JACK_OF_HEARTS;
		final Card card2 = Card.TEN_OF_HEARTS;

		Trick trick = mock(Trick.class);
		when(trick.getTrump()).thenReturn(Suit.HEARTS);
		when(trick.getLeadingSuit()).thenReturn(Suit.SPADES);

		assertTrue(card1.isHigherThan(trick, card2));
	}

	private Trick createMockTrick(final Suit leadingSuit, final Suit trump) {
		Trick trick = mock(Trick.class);

		when(trick.getLeadingSuit()).thenReturn(leadingSuit);
		when(trick.getTrump()).thenReturn(trump);

		return trick;
	}

}
