
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

public class TestStockScore {
	
	@Test
	public void testStockScore() {
		Suit testTrump = new Clubs();
		Trick testTrick = new Trick();
		testTrick.addCard(new Card(new Hearts(), new Seven()));
		testTrick.addCard(new Card(testTrump, new Jack()));
		testTrick.addCard(new Card(new Diamonds(), new Ten()));
		testTrick.addCard(new Card(new Spades(), new Queen()));

		List<Trick> testTricks = new LinkedList<Trick>();
		testTricks.add(testTrick);

		Points actual = new Points(StockScore.calculateScore(testTricks, testTrump));
		Points expected = new Points(33);

		assertEquals(expected.getPoints(), actual.getPoints());
	}

}
