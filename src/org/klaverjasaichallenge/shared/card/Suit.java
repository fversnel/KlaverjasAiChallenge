package org.klaverjasaichallenge.shared.card;

import java.util.Arrays;
import java.util.LinkedList;
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

	public boolean equals(Suit toCompare) {
		return this == toCompare;
	}

	@Override
	public String toString() {
		return this.prettyPrintName;
	}
	
	public static List<Suit> asList() {
		final List<Suit> suits = new LinkedList<Suit>();
		suits.addAll(Arrays.asList(Suit.values()));

		return suits;
	}

}
