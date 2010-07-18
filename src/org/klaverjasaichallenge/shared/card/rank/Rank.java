package org.klaverjasaichallenge.shared.card.rank;

import org.klaverjasaichallenge.shared.Order;
import org.klaverjasaichallenge.shared.Points;

public abstract class Rank {
	protected String name;
	
	protected Order normalOrder;
	protected Order trumpOrder;
	
	protected Points normalPoints;
	protected Points trumpPoints;
	
	public Points getNormalPoints() {
		return this.normalPoints;
	}
	
	public Points getTrumpPoints() {
		return this.trumpPoints;
	}
	
	public String toString() {
		return this.name;
	}
}
