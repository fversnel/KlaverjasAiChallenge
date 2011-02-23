package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

/**
 *
 * @author Frank Versnel
 */
public class Queen extends Rank {
	private static final String NAME = "Queen";

	public Queen() {
		super(NAME, new Order(5), new Order(3), new Order(6), new Points(3),
				new Points(3));
	}

}
