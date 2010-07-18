package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Seven extends Rank {

	public Seven() {
		this.name = "SEVEN";
		
		this.normalOrder = new Order(1);
		this.trumpOrder = new Order(1);
		
		this.normalPoints = new Points(0);
		this.trumpPoints = new Points(0);
	}

}
