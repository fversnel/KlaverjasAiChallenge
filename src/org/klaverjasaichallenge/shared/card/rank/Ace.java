package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

/**
 *
 * @author Frank Versnel
 */
public class Ace extends Rank {
	private static final String NAME = "Ace";

	public Ace() {
		super(NAME, new Order(8), new Order(6), new Order(8), new Points(11),
				new Points(11));
	}

}
