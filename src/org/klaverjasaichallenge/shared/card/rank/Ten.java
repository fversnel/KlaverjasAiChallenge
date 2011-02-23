package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

/**
 *
 * @author Frank Versnel
 */
public class Ten extends Rank {
	private static final String NAME = "Ten";

	public Ten() {
		super(NAME, new Order(7), new Order(5), new Order(4), new Points(10),
				new Points(10));
	}

}
