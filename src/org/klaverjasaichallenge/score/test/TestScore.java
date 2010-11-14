
package org.klaverjasaichallenge.score.test;

import static org.junit.Assert.*;

import org.junit.*;
import org.klaverjasaichallenge.FakePlayer;
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
		this.addCard(new Card(new Hearts(), new Seven()));
		this.addCard(new Card(this.testTrump, new Jack()));
		this.addCard(new Card(new Diamonds(), new Ten()));
		this.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateStockScore(this.testTrick, this.testTrump));
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStuk() {
		this.addCard(new Card(new Clubs(), new Jack()));
		this.addCard(new Card(this.testTrump, new Queen()));
		this.addCard(new Card(new Diamonds(), new Ten()));
		this.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNotStuk() {
		this.addCard(new Card(new Clubs(), new Queen()));
		this.addCard(new Card(this.testTrump, new Queen()));
		this.addCard(new Card(this.testTrump, new Jack()));
		this.addCard(new Card(new Spades(), new Eight()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourJacks() {
		this.addCard(new Card(new Clubs(), new Jack()));
		this.addCard(new Card(new Hearts(), new Jack()));
		this.addCard(new Card(new Diamonds(), new Jack()));
		this.addCard(new Card(new Spades(), new Jack()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(200);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		this.addCard(new Card(new Clubs(), new Queen()));
		this.addCard(new Card(new Hearts(), new Queen()));
		this.addCard(new Card(new Diamonds(), new Queen()));
		this.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsNotSameRank() {
		this.addCard(new Card(new Clubs(), new Queen()));
		this.addCard(new Card(new Hearts(), new Queen()));
		this.addCard(new Card(new Diamonds(), new Queen()));
		this.addCard(new Card(new Spades(), new Ten()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourConsecutiveCardsScore() {
		this.addCard(new Card(new Clubs(), new Queen()));
		this.addCard(new Card(new Clubs(), new Ten()));
		this.addCard(new Card(new Clubs(), new Jack()));
		this.addCard(new Card(new Clubs(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(50);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testThreeConsecutiveCardsScore() {
		this.addCard(new Card(new Hearts(), new Seven()));
		this.addCard(new Card(new Hearts(), new Ten()));
		this.addCard(new Card(new Hearts(), new Jack()));
		this.addCard(new Card(new Hearts(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore() {
		this.addCard(new Card(new Hearts(), new Seven()));
		this.addCard(new Card(new Clubs(), new Ten()));
		this.addCard(new Card(new Hearts(), new Jack()));
		this.addCard(new Card(new Hearts(), new Nine()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore2() {
		Suit trump = new Diamonds();
		this.addCard(new Card(new Spades(), new Nine()));
		this.addCard(new Card(new Spades(), new King()));
		this.addCard(new Card(new Spades(), new Ten()));
		this.addCard(new Card(new Spades(), new Queen()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, trump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore() {
		this.addCard(new Card(this.testTrump, new Queen()));
		this.addCard(new Card(this.testTrump, new Seven()));
		this.addCard(new Card(this.testTrump, new Jack()));
		this.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(40);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore2() {
		this.addCard(new Card(this.testTrump, new Seven()));
		this.addCard(new Card(this.testTrump, new Nine()));
		this.addCard(new Card(this.testTrump, new Eight()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndFourConsecutiveCardsScore() {
		this.addCard(new Card(this.testTrump, new Queen()));
		this.addCard(new Card(this.testTrump, new Ten()));
		this.addCard(new Card(this.testTrump, new Jack()));
		this.addCard(new Card(this.testTrump, new King()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(70);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testHalfTrick() {
		this.addCard(new Card(new Clubs(), new Jack()));
		this.addCard(new Card(new Diamonds(), new Eight()));

		Points actual = new Points(Score.calculateRoemScore(this.testTrick, this.testTrump));
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	private void addCard(final Card card) {
		this.testTrick.addCard(new FakePlayer(), card);
	}

}
