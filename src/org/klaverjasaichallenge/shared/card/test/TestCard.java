
package org.klaverjasaichallenge.shared.card.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

public class TestCard {
	private List<Card> cardsToTest;
	private Suit trump;

	@Before
	public void setUp() {
		this.cardsToTest = new LinkedList<Card>();
		this.trump = new Hearts();
	}

	@Test
	public void testMax() {
		final Card expected = new Card(this.trump, new Jack());

		this.cardsToTest.add(new Card(new Clubs(), new Ace()));
		this.cardsToTest.add(expected);
		this.cardsToTest.add(new Card(new Diamonds(), new Jack()));
		this.cardsToTest.add(new Card(this.trump, new Ten()));

		final Card actual = Card.max(this.trump, this.cardsToTest);

		assert(expected.equals(actual));
	}

}
