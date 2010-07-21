package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public abstract class Rank {
	protected final String name;

	protected final Order normalOrder;
	protected final Order trumpOrder;
	protected final Order roemOrder;

	protected final Points normalPoints;
	protected final Points trumpPoints;

	public Rank(final String name, final Order normalOrder, final Order
			trumpOrder, final Order roemOrder, final Points normalPoints, 
			final Points trumpPoints) {
		this.name = name;

		// TODO Make this an object
		this.normalOrder = normalOrder;
		this.trumpOrder = trumpOrder;
		this.roemOrder = roemOrder;

		// TODO Make this an object
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

	public String toString() {
		return this.name;
	}
}
