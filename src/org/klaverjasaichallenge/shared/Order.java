package org.klaverjasaichallenge.shared;

public class Order {
	private final int order;
	
	public Order(final int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return this.order;
	}
	
	public boolean isHigher(final Order order) {
		return this.getOrder() < order.getOrder();
	}
}
