
package org.klaverjasaichallenge.shared.card.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

public class TestCard {

	@Test
	public void testMax() {
		List<Card> cardsToTest = new LinkedList<Card>();
		Hearts trump = new Hearts();
	    Suit leadingSuit = new Clubs();
		final Card expected = new Card(trump, new Jack());
		cardsToTest.add(new Card(new Clubs(), new Ace()));
		cardsToTest.add(expected);
		cardsToTest.add(new Card(new Diamonds(), new Jack()));
		cardsToTest.add(new Card(trump, new Ten()));
		final Card actual = Card.max(trump, leadingSuit, cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testMax2() {
		List<Card> cardsToTest = new LinkedList<Card>();
		final Card expected = new Card(new Clubs(), new Seven());
		Suit leadingSuit = new Spades();
		cardsToTest.add(new Card(new Spades(), new Jack()));
		cardsToTest.add(new Card(new Spades(), new Queen()));
		cardsToTest.add(new Card(new Spades(), new King()));
		cardsToTest.add(expected);
		final Card actual = Card.max(new Clubs(),leadingSuit, cardsToTest);
		assertEquals(expected,actual);
	}

	@Test
	public void testHigherThan() {
		final Card card1 = new Card(new Hearts(), new Jack());
		final Card card2 = new Card(new Hearts(), new Ten());
		assertTrue(card1.isHigherThan(new Hearts(), new Spades(), card2));
	}

}
