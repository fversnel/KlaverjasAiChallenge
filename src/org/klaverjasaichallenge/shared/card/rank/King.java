package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class King extends Rank {

	public King() {
		this.name = "KING";
		
		this.normalOrder = new Order(6);
		this.trumpOrder = new Order(4);
		
		this.normalPoints = new Points(4);
		this.trumpPoints = new Points(4);
	}

}
