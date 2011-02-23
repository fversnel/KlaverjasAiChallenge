package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

/**
 *
 * @author Frank Versnel
 */
public class King extends Rank {
	private static final String NAME = "King";

	public King() {
		super(NAME, new Order(6), new Order(4), new Order(7), new Points(4),
				new Points(4));
	}

}
