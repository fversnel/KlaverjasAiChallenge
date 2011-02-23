package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

/**
 *
 * @author Frank Versnel
 */
public class Nine extends Rank {
	private static final String NAME = "Nine";

	public Nine() {
		super(NAME, new Order(3), new Order(7), new Order(3), new Points(0),
				new Points(14));
	}

}
