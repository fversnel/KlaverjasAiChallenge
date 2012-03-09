package org.klaverjasaichallenge.engine.score;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;

import org.klaverjasaichallenge.engine.round.EngineTrick;
import org.klaverjasaichallenge.engine.score.Score;
import org.klaverjasaichallenge.shared.Player;
import org.klaverjasaichallenge.shared.Points;
import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.card.Card;
import org.klaverjasaichallenge.shared.card.Suit;

/**
 *
 * @author Frank Versnel
 */
public class TestScore {
	private static final boolean IS_LAST_TRICK = false;

	private Suit testTrump;

	@Before
	public void setUp() {
		this.testTrump = Suit.HEARTS;
	}

	@Test
	public void testStockScore() {
		Trick trick = this.createMockTrick(Card.SEVEN_OF_HEARTS, Card.JACK_OF_HEARTS,
				Card.TEN_OF_DIAMONDS, Card.QUEEN_OF_SPADES);

		this.testExpectedStockPoints(trick, new Points(33));
	}

	@Test
	public void testStuk() {
		Trick trick = this.createMockTrick(Card.JACK_OF_CLUBS, Card.QUEEN_OF_HEARTS,
				Card.TEN_OF_DIAMONDS, Card.KING_OF_HEARTS);

		this.testExpectedRoemPoints(trick, new Points(20));
	}

	@Test
	public void testNotStuk() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_HEARTS,
				Card.JACK_OF_HEARTS, Card.EIGHT_OF_SPADES);

		this.testExpectedRoemPoints(trick, new Points(0));
	}

	@Test
	public void testFourJacks() {
		Trick trick = this.createMockTrick(Card.JACK_OF_CLUBS, Card.JACK_OF_HEARTS,
				Card.JACK_OF_DIAMONDS, Card.JACK_OF_SPADES);

		this.testExpectedRoemPoints(trick, new Points(200));
	}

	@Test
	public void testFourCardsSameRank() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_HEARTS,
				Card.QUEEN_OF_DIAMONDS, Card.QUEEN_OF_SPADES);

		this.testExpectedRoemPoints(trick, new Points(100));
	}

	@Test
	public void testFourCardsNotSameRank() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_CLUBS, Card.QUEEN_OF_HEARTS,
				Card.QUEEN_OF_DIAMONDS, Card.TEN_OF_SPADES);

		this.testExpectedRoemPoints(trick, new Points(0));
	}

	@Test
	public void testFourConsecutiveCardsScore() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_CLUBS, Card.TEN_OF_CLUBS,
				Card.JACK_OF_CLUBS, Card.NINE_OF_CLUBS);

		this.testExpectedRoemPoints(trick, new Points(50));
	}

	@Test
	public void testThreeConsecutiveCardsScore() {
		Trick trick = this.createMockTrick(Card.SEVEN_OF_HEARTS, Card.TEN_OF_HEARTS,
				Card.JACK_OF_HEARTS, Card.NINE_OF_HEARTS);

		this.testExpectedRoemPoints(trick, new Points(20));
	}

	@Test
	public void testNoThreeConsecutiveCardsScore() {
		Trick trick = this.createMockTrick(Card.SEVEN_OF_HEARTS, Card.TEN_OF_CLUBS,
				Card.JACK_OF_CLUBS, Card.NINE_OF_HEARTS);

		this.testExpectedRoemPoints(trick, new Points(0));
	}

	@Test
	public void testNoThreeConsecutiveCardsScore2() {
		Trick trick = this.createMockTrick(Card.NINE_OF_SPADES, Card.KING_OF_SPADES,
				Card.TEN_OF_SPADES, Card.QUEEN_OF_SPADES);

		this.testExpectedRoemPoints(trick, new Points(0));
	}

	@Test
	public void testStukAndThreeConsecutiveCardsScore() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_HEARTS, Card.SEVEN_OF_HEARTS,
				Card.JACK_OF_HEARTS, Card.KING_OF_HEARTS);

		this.testExpectedRoemPoints(trick, new Points(40));
	}

	@Test
	public void testStukAndFourConsecutiveCardsScore() {
		Trick trick = this.createMockTrick(Card.QUEEN_OF_HEARTS, Card.TEN_OF_HEARTS,
				Card.JACK_OF_HEARTS, Card.KING_OF_HEARTS);

		this.testExpectedRoemPoints(trick, new Points(70));
	}

	private void testExpectedStockPoints(final Trick trick, final Points expected) {
		Points actual = new Score(trick).getStockScore();

		assertEquals(expected, actual);
	}

	private void testExpectedRoemPoints(final Trick trick, final Points expected) {
		Points actual = new Score(trick).getRoemScore();

		assertEquals(expected, actual);
	}

	private Trick createMockTrick(final Card... cards) {
		final EngineTrick trick = new EngineTrick(this.testTrump, IS_LAST_TRICK);

		for(Card card : cards) {
			trick.addCard(mock(Player.class), card);
		}

		return trick;
	}

}
