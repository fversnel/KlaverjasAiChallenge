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
import org.klaverjasaichallenge.shared.card.suit.*;
import org.klaverjasaichallenge.shared.card.rank.*;

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
		Card trickCard = new Card(new Hearts(), new Queen());
		Trick trick = this.createMockTrick(new Hearts(), new Hearts(),
				trickCard, trickCard);

		Card cardToPlay = new Card(new Hearts(), new Seven());
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(new Hearts(), new Jack()));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerFollowsLeadingSuit() {
		Card trickCard = new Card(new Hearts(), new Queen());
		Trick trick = this.createMockTrick(new Hearts(), new Spades(),
				trickCard, null);

		Card cardToPlay = new Card(new Spades(), new Seven());
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(new Hearts(), new Jack()));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerHasToTrump() {
		Card trickCard = new Card(new Hearts(), new Queen());
		Trick trick = this.createMockTrick(new Hearts(), new Spades(),
				trickCard, null);

		Card cardToPlay = new Card(new Diamonds(), new Seven());
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(new Spades(), new Eight()));
		cardsInHand.add(new Card(new Diamonds(), new Jack()));

		assertFalse(this.ruleSet.isLegalCard(trick, cardToPlay, cardsInHand));
	}

	@Test
	public void testIfPlayerCanPlayAnyCard() {
		Card trickCard = new Card(new Hearts(), new Queen());
		Trick trick = this.createMockTrick(new Hearts(), new Spades(),
				trickCard, null);

		Card cardToPlay = new Card(new Spades(), new Queen());
		List<Card> cardsInHand = new LinkedList<Card>();
		cardsInHand.add(cardToPlay);
		cardsInHand.add(new Card(new Diamonds(), new Jack()));
		cardsInHand.add(new Card(new Diamonds(), new Ace()));

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
