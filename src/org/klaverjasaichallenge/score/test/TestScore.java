
package org.klaverjasaichallenge.score.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.score.Score;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

public class TestScore {
	private Suit testTrump;
	private Trick testTrick;
	private List<Trick> testTricks;

	@Before
	public void setUp() {
		this.testTrump = new Hearts();
		this.testTrick = new Trick();
		this.testTricks = new LinkedList<Trick>();
		this.testTricks.add(this.testTrick);
	}

	@Test
	public void testStockScore() {
		testTrick.addCard(new Card(new Hearts(), new Seven()));
		testTrick.addCard(new Card(testTrump, new Jack()));
		testTrick.addCard(new Card(new Diamonds(), new Ten()));
		testTrick.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateStockScore(testTricks, testTrump));
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStuk() {
		this.testTrick.addCard(new Card(new Clubs(), new Jack()));
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Ten()));
		this.testTrick.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTricks, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNotStuk() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Jack()));
		this.testTrick.addCard(new Card(new Spades(), new Eight()));

		Points actual = new Points(Score.calculateRoemScore(this.testTricks, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourJacks() {
		this.testTrick.addCard(new Card(new Clubs(), new Jack()));
		this.testTrick.addCard(new Card(new Hearts(), new Jack()));
		this.testTrick.addCard(new Card(new Diamonds(), new Jack()));
		this.testTrick.addCard(new Card(new Spades(), new Jack()));

		Points actual = new Points(Score.calculateRoemScore(this.testTricks, this.testTrump));
		Points expected = new Points(200);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(new Hearts(), new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Queen()));
		this.testTrick.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateRoemScore(this.testTricks, this.testTrump));
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsNotSameRank() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(new Hearts(), new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Queen()));
		this.testTrick.addCard(new Card(new Spades(), new Ten()));

		Points actual = new Points(Score.calculateRoemScore(this.testTricks, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testMarchScore() {
		for(int i = 1; i < 8; i++) {
			this.testTricks.add(new Trick());
		}

		Points actual = new Points(Score.calculateMarchScore(this.testTricks));
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

}
