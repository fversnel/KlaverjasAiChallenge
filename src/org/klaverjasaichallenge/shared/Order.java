package org.klaverjasaichallenge.shared;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class Order implements Comparable {
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

	public int compareTo(Object o) {
		Order toCompare = (Order)o;
		if(this.isHigherThan(toCompare)) {
			return 1;
		} else if (this.equals(toCompare)) {
			return 0;
		} else {
			return -1;
		}
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

	public static void sort(List<Order> orders) {
		Collections.sort(orders);
	}

}
