package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Nine extends Rank {

	public Nine() {
		this.name = "NINE";
		
		this.normalOrder = new Order(3);
		this.trumpOrder = new Order(7);
		
		this.normalPoints = new Points(0);
		this.trumpPoints = new Points(14);
	}

}
