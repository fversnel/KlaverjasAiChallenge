package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Queen extends Rank {

	public Queen() {
		this.name = "QUEEN";
		
		this.normalOrder = new Order(5);
		this.trumpOrder = new Order(3);
		
		this.normalPoints = new Points(3);
		this.trumpPoints = new Points(3);
	}

}
