package org.klaverjasaichallenge.shared.card.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Rank;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestCard {

	@Test
	public void testMax() {
		List<Card> cardsToTest = new LinkedList<Card>();
		Suit trump = Suit.HEARTS;
		Suit leadingSuit = Suit.CLUBS;
		final Card expected = new Card(trump, Rank.JACK);
		cardsToTest.add(new Card(Suit.CLUBS, Rank.ACE));
		cardsToTest.add(expected);
		cardsToTest.add(new Card(Suit.DIAMONDS, Rank.JACK));
		cardsToTest.add(new Card(trump, Rank.TEN));
		final Card actual = Card.max(trump, leadingSuit, cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testMax2() {
		List<Card> cardsToTest = new LinkedList<Card>();
		final Card expected = new Card(Suit.CLUBS, Rank.SEVEN);
		Suit leadingSuit = Suit.SPADES;
		cardsToTest.add(new Card(Suit.SPADES, Rank.JACK));
		cardsToTest.add(new Card(Suit.SPADES, Rank.QUEEN));
		cardsToTest.add(new Card(Suit.SPADES, Rank.KING));
		cardsToTest.add(expected);
		final Card actual = Card.max(Suit.CLUBS,leadingSuit, cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testHigherThan() {
		final Card card1 = new Card(Suit.HEARTS, Rank.JACK);
		final Card card2 = new Card(Suit.HEARTS, Rank.TEN);
		assertTrue(card1.isHigherThan(Suit.HEARTS, Suit.SPADES, card2));
	}

}
