package org.klaverjasaichallenge.engine.score.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.klaverjasaichallenge.engine.round.Trick;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;
import org.klaverjasaichallenge.shared.card.Rank;

/**
 *
 * @author Frank Versnel
 */
public class TestScore {
	private static final boolean IS_LAST_TRICK = false;

	private Suit testTrump;
	private Trick testTrick;

	@Before
	public void setUp() {
		this.testTrump = Suit.HEARTS;
		this.testTrick = new Trick(this.testTrump, IS_LAST_TRICK);
	}

	@Test
	public void testStockScore() {
		this.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
		this.addCard(new Card(this.testTrump, Rank.JACK));
		this.addCard(new Card(Suit.DIAMONDS, Rank.TEN));
		this.addCard(new Card(Suit.SPADES, Rank.QUEEN));

		Points actual = new Score(this.testTrick).getStockScore();
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStuk() {
		this.addCard(new Card(Suit.CLUBS, Rank.JACK));
		this.addCard(new Card(this.testTrump, Rank.QUEEN));
		this.addCard(new Card(Suit.DIAMONDS, Rank.TEN));
		this.addCard(new Card(this.testTrump, Rank.KING));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNotStuk() {
		this.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
		this.addCard(new Card(this.testTrump, Rank.QUEEN));
		this.addCard(new Card(this.testTrump, Rank.JACK));
		this.addCard(new Card(Suit.SPADES, Rank.EIGHT));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourJacks() {
		this.addCard(new Card(Suit.CLUBS, Rank.JACK));
		this.addCard(new Card(Suit.HEARTS, Rank.JACK));
		this.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
		this.addCard(new Card(Suit.SPADES, Rank.JACK));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(200);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		this.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
		this.addCard(new Card(Suit.HEARTS, Rank.QUEEN));
		this.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
		this.addCard(new Card(Suit.SPADES, Rank.QUEEN));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsNotSameRank() {
		this.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
		this.addCard(new Card(Suit.HEARTS, Rank.QUEEN));
		this.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
		this.addCard(new Card(Suit.SPADES, Rank.TEN));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourConsecutiveCardsScore() {
		this.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
		this.addCard(new Card(Suit.CLUBS, Rank.TEN));
		this.addCard(new Card(Suit.CLUBS, Rank.JACK));
		this.addCard(new Card(Suit.CLUBS, Rank.NINE));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(50);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testThreeConsecutiveCardsScore() {
		this.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
		this.addCard(new Card(Suit.HEARTS, Rank.TEN));
		this.addCard(new Card(Suit.HEARTS, Rank.JACK));
		this.addCard(new Card(Suit.HEARTS, Rank.NINE));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore() {
		this.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
		this.addCard(new Card(Suit.CLUBS, Rank.TEN));
		this.addCard(new Card(Suit.HEARTS, Rank.JACK));
		this.addCard(new Card(Suit.HEARTS, Rank.NINE));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore2() {
		this.addCard(new Card(Suit.SPADES, Rank.NINE));
		this.addCard(new Card(Suit.SPADES, Rank.KING));
		this.addCard(new Card(Suit.SPADES, Rank.TEN));
		this.addCard(new Card(Suit.SPADES, Rank.QUEEN));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore() {
		this.addCard(new Card(this.testTrump, Rank.QUEEN));
		this.addCard(new Card(this.testTrump, Rank.SEVEN));
		this.addCard(new Card(this.testTrump, Rank.JACK));
		this.addCard(new Card(this.testTrump, Rank.KING));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(40);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore2() {
		this.addCard(new Card(this.testTrump, Rank.SEVEN));
		this.addCard(new Card(this.testTrump, Rank.NINE));
		this.addCard(new Card(this.testTrump, Rank.EIGHT));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndFourConsecutiveCardsScore() {
		this.addCard(new Card(this.testTrump, Rank.QUEEN));
		this.addCard(new Card(this.testTrump, Rank.TEN));
		this.addCard(new Card(this.testTrump, Rank.JACK));
		this.addCard(new Card(this.testTrump, Rank.KING));

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(70);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	private void addCard(final Card card) {
		this.testTrick.addCard(mock(Player.class), card);
	}

}
