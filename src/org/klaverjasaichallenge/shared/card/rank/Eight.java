package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Eight extends Rank {

	public Eight() {
		this.name = "EIGHT";
		
		this.normalOrder = new Order(2);
		this.trumpOrder = new Order(2);
		
		this.normalPoints = new Points(0);
		this.trumpPoints = new Points(0);
	}

}
