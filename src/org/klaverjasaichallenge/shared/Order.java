package org.klaverjasaichallenge.shared;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class Order {
	private final int order;
	
	public Order(final int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return this.order;
	}
	
	public boolean isHigherThan(final Order order) {
		return this.getOrder() > order.getOrder();
	}

	public boolean isHigherOrSameAs(final Order order) {
		return this.getOrder() >= order.getOrder();
	}

	public boolean equals(final Order toCompare) {
		return this.order == toCompare.getOrder();
	}

	public static Order minus(final Order leftHandSide, final Order rightHandSide) {
		return new Order(leftHandSide.getOrder() - rightHandSide.getOrder());
	}

	public static List<Order> sort(final List<Order> orders) {
		int[] intOrders = new int[orders.size()];
		for(int i = 0; i < orders.size(); i++) {
			int intOrder = orders.get(i).getOrder();
			intOrders[i] = intOrder;
		}

		Arrays.sort(intOrders);

		List<Order> sortedOrders = new LinkedList<Order>();
		for(int i = 0; i < intOrders.length; i++) {
			sortedOrders.add(new Order(intOrders[i]));
		}

		return sortedOrders;
	}

}
