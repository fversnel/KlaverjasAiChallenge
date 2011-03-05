package org.klaverjasaichallenge.shared.card;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public enum Rank {
	SEVEN(new Order(1), new Order(1), new Order(1), new Points(0),
			new Points(0)),
	EIGHT(new Order(2), new Order(2), new Order(2), new Points(0),
				new Points(0)),
	NINE(new Order(3), new Order(7), new Order(3), new Points(0),
				new Points(14)),
	TEN(new Order(7), new Order(5), new Order(4), new Points(10),
				new Points(10)),
	JACK(new Order(4), new Order(8), new Order(5), new Points(2),
				new Points(20)),
	QUEEN(new Order(5), new Order(3), new Order(6), new Points(3),
				new Points(3)),
	KING(new Order(6), new Order(4), new Order(7), new Points(4),
				new Points(4)),
	ACE(new Order(8), new Order(6), new Order(8), new Points(11),
				new Points(11));

	private Order normalOrder;
	private Order roemOrder;
	private Order trumpOrder;

	private final Points normalPoints;
	private final Points trumpPoints;

	private Rank(final Order normalOrder, final Order trumpOrder,
			final Order roemOrder, final Points normalPoints,
			final Points trumpPoints) {
		this.normalOrder = normalOrder;
		this.trumpOrder = trumpOrder;
		this.roemOrder = roemOrder;

		this.normalPoints = normalPoints;
		this.trumpPoints = trumpPoints;
	}

	public Order getNormalOrder() {
		return this.normalOrder;
	}

	public Order getTrumpOrder() {
		return this.trumpOrder;
	}

	public Order getRoemOrder() {
		return this.roemOrder;
	}

	public Points getNormalPoints() {
		return this.normalPoints;
	}

	public Points getTrumpPoints() {
		return this.trumpPoints;
	}

	public boolean equals(final Rank toCompare) {
		return this == toCompare;
	}

}
