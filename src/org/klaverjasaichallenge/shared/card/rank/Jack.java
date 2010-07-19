package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Jack extends Rank {

	private static final String NAME = "Jack";

	public Jack() {
		super(NAME, new Order(4), new Order(8), new Order(5), new Points(2),
				new Points(20));
	}

}
