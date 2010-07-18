package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public class Ace extends Rank {

	public Ace() {
		this.name = "ACE";
		
		this.normalOrder = new Order(8);
		this.trumpOrder = new Order(6);
		
		this.normalPoints = new Points(11);
		this.trumpPoints = new Points(11);
	}

}
