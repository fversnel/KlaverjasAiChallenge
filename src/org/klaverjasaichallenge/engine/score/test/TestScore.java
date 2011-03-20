package org.klaverjasaichallenge.engine.score.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import  org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestScore {
	private static final boolean IS_LAST_TRICK = false;

	private Suit testTrump;
	private EngineTrick testTrick;

	@Before
	public void setUp() {
		this.testTrump = Suit.HEARTS;
		this.testTrick = new EngineTrick(this.testTrump, IS_LAST_TRICK);
	}

	@Test
	public void testStockScore() {
		this.addCard(Card.SEVEN_OF_HEARTS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.TEN_OF_DIAMONDS);
		this.addCard(Card.QUEEN_OF_SPADES);

		Points actual = new Score(this.testTrick).getStockScore();
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStuk() {
		this.addCard(Card.JACK_OF_CLUBS);
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.TEN_OF_DIAMONDS);
		this.addCard(Card.KING_OF_HEARTS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNotStuk() {
		this.addCard(Card.QUEEN_OF_CLUBS);
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.EIGHT_OF_SPADES);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourJacks() {
		this.addCard(Card.JACK_OF_CLUBS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.JACK_OF_DIAMONDS);
		this.addCard(Card.JACK_OF_SPADES);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(200);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsSameRank() {
		this.addCard(Card.QUEEN_OF_CLUBS);
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.QUEEN_OF_DIAMONDS);
		this.addCard(Card.QUEEN_OF_SPADES);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(100);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourCardsNotSameRank() {
		this.addCard(Card.QUEEN_OF_CLUBS);
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.QUEEN_OF_DIAMONDS);
		this.addCard(Card.TEN_OF_SPADES);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testFourConsecutiveCardsScore() {
		this.addCard(Card.QUEEN_OF_CLUBS);
		this.addCard(Card.TEN_OF_CLUBS);
		this.addCard(Card.JACK_OF_CLUBS);
		this.addCard(Card.NINE_OF_CLUBS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(50);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testThreeConsecutiveCardsScore() {
		this.addCard(Card.SEVEN_OF_HEARTS);
		this.addCard(Card.TEN_OF_HEARTS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.NINE_OF_HEARTS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(20);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore() {
		this.addCard(Card.SEVEN_OF_HEARTS);
		this.addCard(Card.TEN_OF_CLUBS);
		this.addCard(Card.JACK_OF_CLUBS);
		this.addCard(Card.NINE_OF_HEARTS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testNoThreeConsecutiveCardsScore2() {
		this.addCard(Card.NINE_OF_SPADES);
		this.addCard(Card.KING_OF_SPADES);
		this.addCard(Card.TEN_OF_SPADES);
		this.addCard(Card.QUEEN_OF_SPADES);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(0);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore() {
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.SEVEN_OF_HEARTS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.KING_OF_HEARTS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(40);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	@Test
	public void testStukAndFourConsecutiveCardsScore() {
		this.addCard(Card.QUEEN_OF_HEARTS);
		this.addCard(Card.TEN_OF_HEARTS);
		this.addCard(Card.JACK_OF_HEARTS);
		this.addCard(Card.KING_OF_HEARTS);

		Points actual = new Score(this.testTrick).getRoemScore();
		Points expected = new Points(70);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

	private void addCard(final Card card) {
		this.testTrick.addCard(mock(Player.class), card);
	}

}
