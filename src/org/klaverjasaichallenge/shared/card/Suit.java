package org.klaverjasaichallenge.shared.card;

public enum Suit {
	SPADES("Spades"),
	CLUBS("Clubs"),
	HEARTS("Hearts"),
	DIAMONDS("Diamonds");

	protected final String prettyPrintName;

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

}
