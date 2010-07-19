package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Seven extends Rank {
	private static final String NAME = "Seven";

	public Seven() {
		super(NAME, new Order(1), new Order(1), new Order(1), new Points(0),
				new Points(0));
	}

}
