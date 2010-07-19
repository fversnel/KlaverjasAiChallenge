package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Eight extends Rank {

	private static final String NAME = "Eight";

	public Eight() {
		super(NAME, new Order(2), new Order(2), new Order(2), new Points(0),
				new Points(0));
	}

}
