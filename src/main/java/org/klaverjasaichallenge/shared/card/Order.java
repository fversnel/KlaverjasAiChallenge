package org.klaverjasaichallenge.shared.card;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Frank Versnel
 */
public class Order implements Comparable<Order> {
	protected final int order;

	public Order(final int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}

	public boolean isHigherThan(final Order order) {
		return this.getOrder() > order.getOrder();
	}

	@Override
	public int compareTo(Order toCompare) {
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

	@Override
	public boolean equals(final Object o) {
		Order toCompare = (Order)o;
		return this.order == toCompare.getOrder();
	}

	public static Order minus(final Order leftHandSide, final Order rightHandSide) {
		return new Order(leftHandSide.getOrder() - rightHandSide.getOrder());
	}

	public static void sort(List<Order> orders) {
		Collections.sort(orders);
	}

}
