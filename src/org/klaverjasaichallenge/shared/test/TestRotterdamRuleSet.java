package org.klaverjasaichallenge.shared.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.LinkedList;

import org.junit.*;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.RuleSet;
import org.klaverjasaichallenge.shared.RotterdamRuleSet;
import org.klaverjasaichallenge.shared.card.*;

/**
 *
 * @author Frank Versnel
 */
public class TestRotterdamRuleSet {
	RuleSet ruleSet;

	@Before
	public void setUp() {
		this.ruleSet = new RotterdamRuleSet();
	}

	@Test
	public void testIfPlayerCanUnderTrump() {
		Card trickCard = new Card(Suit.HEARTS, Rank.QUEEN);
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.HEARTS,
				trickCard, trickCard);

		Card cardToPlay = new Card(Suit.HEARTS, Rank.SEVEN);
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(Suit.HEARTS, Rank.JACK));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerFollowsLeadingSuit() {
		Card trickCard = new Card(Suit.HEARTS, Rank.QUEEN);
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = new Card(Suit.SPADES, Rank.SEVEN);
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(Suit.HEARTS, Rank.JACK));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerHasToTrump() {
		Card trickCard = new Card(Suit.HEARTS, Rank.QUEEN);
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = new Card(Suit.DIAMONDS, Rank.SEVEN);
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(Suit.SPADES, Rank.EIGHT));
		cardsInHand.add(new Card(Suit.DIAMONDS, Rank.JACK));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerCanPlayAnyCard() {
		Card trickCard = new Card(Suit.HEARTS, Rank.QUEEN);
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = new Card(Suit.SPADES, Rank.QUEEN);
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(Suit.DIAMONDS, Rank.JACK));
		cardsInHand.add(new Card(Suit.DIAMONDS, Rank.ACE));

		assertTrue(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	private Trick createMockTrick(final Suit leadingSuit, final Suit trump,
			final Card highestCard, final Card highestTrump) {
		Trick trick = mock(Trick.class);
		when(trick.getLeadingSuit()).thenReturn(leadingSuit);
		when(trick.getTrump()).thenReturn(trump);

		when(trick.getHighestCard()).thenReturn(highestCard);
		when(trick.getHighestTrump()).thenReturn(highestTrump);

		return trick;
	}

}
