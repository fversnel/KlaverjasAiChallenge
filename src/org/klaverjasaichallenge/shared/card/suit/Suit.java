package org.klaverjasaichallenge.shared.card.suit;

public abstract class Suit {
	protected final String name;

	public Suit(final String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
