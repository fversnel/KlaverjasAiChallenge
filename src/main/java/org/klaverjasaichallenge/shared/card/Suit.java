package org.klaverjasaichallenge.shared.card;

import java.util.Arrays;
import java.util.List;

public enum Suit {
	SPADES("Spades"),
	CLUBS("Clubs"),
	HEARTS("Hearts"),
	DIAMONDS("Diamonds");

	private final String prettyPrintName;

	private Suit(final String prettyPrintName) {
		this.prettyPrintName = prettyPrintName;
	}

	@Override
	public String toString() {
		return this.prettyPrintName;
	}

	public static List<Suit> asList() {
		return Arrays.asList(Suit.values());
	}

}
