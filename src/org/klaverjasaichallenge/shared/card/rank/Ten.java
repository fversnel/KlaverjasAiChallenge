package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Ten extends Rank {

	public Ten() {
		this.name = "TEN";
		
		this.normalOrder = new Order(7);
		this.trumpOrder = new Order(5);
		
		this.normalPoints = new Points(10);
		this.trumpPoints = new Points(10);
	}

}
