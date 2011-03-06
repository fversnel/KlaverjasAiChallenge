package org.klaverjasaichallenge.shared.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.LinkedList;

import org.junit.*;

import org.klaverjasaichallenge.shared.Trick;
import org.klaverjasaichallenge.shared.ruleset.RuleSet;
import org.klaverjasaichallenge.shared.ruleset.RotterdamRuleSet;
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
		Card trickCard = Card.QUEEN_OF_HEARTS;
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.HEARTS,
				trickCard, trickCard);

		Card cardToPlay = Card.SEVEN_OF_HEARTS;
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(Card.JACK_OF_HEARTS);

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerFollowsLeadingSuit() {
		Card trickCard = Card.QUEEN_OF_HEARTS;
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = Card.SEVEN_OF_SPADES;
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(Card.JACK_OF_HEARTS);

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerHasToTrump() {
		Card trickCard = Card.QUEEN_OF_HEARTS;
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = Card.SEVEN_OF_DIAMONDS;
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(Card.EIGHT_OF_SPADES);
		cardsInHand.add(Card.JACK_OF_DIAMONDS);

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerCanPlayAnyCard() {
		Card trickCard = Card.QUEEN_OF_HEARTS;
		Trick trick = this.createMockTrick(Suit.HEARTS, Suit.SPADES,
				trickCard, null);

		Card cardToPlay = Card.QUEEN_OF_SPADES;
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(Card.JACK_OF_DIAMONDS);
		cardsInHand.add(Card.ACE_OF_DIAMONDS);

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
