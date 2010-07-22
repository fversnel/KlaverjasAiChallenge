
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

	@Before
	public void setUp() {
		this.testTrump = new Hearts();
		this.testTrick = new Trick();
	}

	@Test
	public void testStockScore() {
		testTrick.addCard(new Card(new Hearts(), new Seven()));
		testTrick.addCard(new Card(testTrump, new Jack()));
		testTrick.addCard(new Card(new Diamonds(), new Ten()));
		testTrick.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateStockScore(this.testTrick, this.testTrump));
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStuk() {
		this.testTrick.addCard(new Card(new Clubs(), new Jack()));
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Ten()));
		this.testTrick.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNotStuk() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Jack()));
		this.testTrick.addCard(new Card(new Spades(), new Eight()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourJacks() {
		this.testTrick.addCard(new Card(new Clubs(), new Jack()));
		this.testTrick.addCard(new Card(new Hearts(), new Jack()));
		this.testTrick.addCard(new Card(new Diamonds(), new Jack()));
		this.testTrick.addCard(new Card(new Spades(), new Jack()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(200);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(new Hearts(), new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Queen()));
		this.testTrick.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsNotSameRank() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(new Hearts(), new Queen()));
		this.testTrick.addCard(new Card(new Diamonds(), new Queen()));
		this.testTrick.addCard(new Card(new Spades(), new Ten()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testMarchScore() {
		List<Trick> testTricks = new LinkedList<Trick>();
		for(int i = 1; i <= 8; i++) {
			testTricks.add(new Trick());
		}

		Points actual = new Points(Score.calculateMarchScore(testTricks));
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourConsecutiveCardsScore() {
		this.testTrick.addCard(new Card(new Clubs(), new Queen()));
		this.testTrick.addCard(new Card(new Clubs(), new Ten()));
		this.testTrick.addCard(new Card(new Clubs(), new Jack()));
		this.testTrick.addCard(new Card(new Clubs(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(50);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testThreeConsecutiveCardsScore() {
		this.testTrick.addCard(new Card(new Hearts(), new Seven()));
		this.testTrick.addCard(new Card(new Hearts(), new Ten()));
		this.testTrick.addCard(new Card(new Hearts(), new Jack()));
		this.testTrick.addCard(new Card(new Hearts(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore() {
		this.testTrick.addCard(new Card(new Hearts(), new Seven()));
		this.testTrick.addCard(new Card(new Clubs(), new Ten()));
		this.testTrick.addCard(new Card(new Hearts(), new Jack()));
		this.testTrick.addCard(new Card(new Hearts(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore() {
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Seven()));
		this.testTrick.addCard(new Card(this.testTrump, new Jack()));
		this.testTrick.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(40);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndFourConsecutiveCardsScore() {
		this.testTrick.addCard(new Card(this.testTrump, new Queen()));
		this.testTrick.addCard(new Card(this.testTrump, new Ten()));
		this.testTrick.addCard(new Card(this.testTrump, new Jack()));
		this.testTrick.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(70);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

}
