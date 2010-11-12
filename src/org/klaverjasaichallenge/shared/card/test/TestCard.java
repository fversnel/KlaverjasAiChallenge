
package org.klaverjasaichallenge.shared.card.test;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

public class TestCard {
	private List<Card> cardsToTest;

	@Before
	public void setUp() {
		this.cardsToTest = new LinkedList<Card>();
	}

	@Test
	public void testMax() {
		Suit trump = new Hearts();
		final Card expected = new Card(trump, new Jack());

		this.cardsToTest.add(new Card(new Clubs(), new Ace()));
		this.cardsToTest.add(expected);
		this.cardsToTest.add(new Card(new Diamonds(), new Jack()));
		this.cardsToTest.add(new Card(trump, new Ten()));

		final Card actual = Card.max(trump, this.cardsToTest);

		assert(expected.equals(actual));
	}

	@Test
	public void testMax2() {
		Suit trump = new Clubs();
		final Card expected = new Card(trump, new Seven());

		this.cardsToTest.add(new Card(new Spades(), new Jack()));
		this.cardsToTest.add(new Card(new Spades(), new Queen()));
		this.cardsToTest.add(new Card(new Spades(), new King()));
		this.cardsToTest.add(expected);

		final Card actual = Card.max(trump, this.cardsToTest);

		assert(expected.equals(actual));
	}

}
