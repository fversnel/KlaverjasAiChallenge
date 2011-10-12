package org.klaverjasaichallenge.shared.card;

import com.google.common.collect.Ordering;

import org.klaverjasaichallenge.shared.Trick;

class CardComparator extends Ordering<Card> {
	private final Suit trump;
	private final Suit leadingSuit;

	public CardComparator(final Trick trick) {
		super();
		this.trump = trick.getTrump();
		this.leadingSuit = trick.getLeadingSuit();
	}

	public CardComparator(final Suit trump) {
		super();
		this.trump = trump;
		this.leadingSuit = trump;
	}

	@Override
	public int compare(Card card1, Card card2) {
		int comparison = 0;

		if(card1.isOfSuit(trump) && card2.isOfSuit(trump)) {
			comparison = card1.getTrumpOrder().isHigherThan(card2.getTrumpOrder()) ?
					1 : -1;
		} else if(card1.isOfSuit(trump) && !card2.isOfSuit(trump)) {
			comparison = 1;
		} else if(!card1.isOfSuit(trump) && card2.isOfSuit(trump)) {
			comparison = -1;
		} else if(card1.isOfSuit(leadingSuit) && card2.isOfSuit(leadingSuit)) {
			comparison = card1.getNormalOrder().isHigherThan(card2.getNormalOrder()) ?
					1 : -1;
		} else if(card1.isOfSuit(leadingSuit) && !card2.isOfSuit(leadingSuit)) {
			comparison = 1;
		} else if(!card1.isOfSuit(leadingSuit) && card2.isOfSuit(leadingSuit)) {
			comparison = -1;
		} else if (!card1.isOfSuit(leadingSuit) && !card2.isOfSuit(leadingSuit)) {
			comparison = -1;
		}

		assert(comparison != 0) : "Card comparison does not handle all possible cases; " +
				"comparing card " + card1 + " to card " + card2 + " failed.";

		return comparison;
	}

}
