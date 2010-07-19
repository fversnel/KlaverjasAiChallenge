
package org.klaverjasaichallenge.score;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.score.Score;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

public class TestSpecialScore {
	
	@Test
	public void testStuk() {
		Suit testTrump = new Hearts();
		Trick testTrick = new Trick();
		testTrick.addCard(new Card(new Clubs(), new Jack()));
		testTrick.addCard(new Card(testTrump, new Queen()));
		testTrick.addCard(new Card(new Diamonds(), new Ten()));
		testTrick.addCard(new Card(testTrump, new King()));

		List<Trick> testTricks = new LinkedList<Trick>();
		testTricks.add(testTrick);

		Points actual = new Points(SpecialScore.calculateScore(testTricks, testTrump));
		Points expected = new Points(20);

		assertEquals(actual.getPoints(), expected.getPoints());
	}

	@Test
	public void testFourJacks() {
		Suit testTrump = new Hearts();
		Trick testTrick = new Trick();
		testTrick.addCard(new Card(new Clubs(), new Jack()));
		testTrick.addCard(new Card(new Hearts(), new Jack()));
		testTrick.addCard(new Card(new Diamonds(), new Jack()));
		testTrick.addCard(new Card(new Spades(), new Jack()));

		List<Trick> testTricks = new LinkedList<Trick>();
		testTricks.add(testTrick);

		Points actual = new Points(SpecialScore.calculateScore(testTricks, testTrump));
		Points expected = new Points(200);

		assertEquals(actual.getPoints(), expected.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		Suit testTrump = new Hearts();
		Trick testTrick = new Trick();
		testTrick.addCard(new Card(new Clubs(), new Queen()));
		testTrick.addCard(new Card(new Hearts(), new Queen()));
		testTrick.addCard(new Card(new Diamonds(), new Queen()));
		testTrick.addCard(new Card(new Spades(), new Queen()));

		List<Trick> testTricks = new LinkedList<Trick>();
		testTricks.add(testTrick);

		Points actual = new Points(SpecialScore.calculateScore(testTricks, testTrump));
		Points expected = new Points(100);

		assertEquals(actual.getPoints(), expected.getPoints());
	}

}
