package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Jack extends Rank {

	public Jack() {
		this.name = "JACK";
		
		this.normalOrder = new Order(4);
		this.trumpOrder = new Order(8);
		
		this.normalPoints = new Points(2);
		this.trumpPoints = new Points(20);
	}

}
