
package org.klaverjasaichallenge.shared.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Order;

public class TestOrder {

	@Test
	public void testSort() {
		List<Order> actualOrders = new LinkedList<Order>();
		actualOrders.add(new Order(5));
		actualOrders.add(new Order(3));
		actualOrders.add(new Order(1));
		actualOrders.add(new Order(8));

		Order.sort(actualOrders);

		int actual = actualOrders.get(0).getOrder();	
		int expected = 1;
		assertEquals(expected, actual);

		actual = actualOrders.get(1).getOrder();
		expected = 3;
		assertEquals(expected, actual);

		actual = actualOrders.get(2).getOrder();
		expected = 5;
		assertEquals(expected, actual);

		actual = actualOrders.get(3).getOrder();
		expected = 8;
		assertEquals(expected, actual);
	}

	@Test
	public void testEquals() {
		Order testOrder1 = new Order(2);
		Order testOrder2 = new Order(2);

		boolean expected = true;
		boolean actual = testOrder1.equals(testOrder2);

		assertEquals(expected, actual);
	}

	@Test
	public void testMinus() {
		Order testOrder1 = new Order(5);
		Order testOrder2 = new Order(3);

		int expected = 2;
		int actual = Order.minus(testOrder1, testOrder2).getOrder();

		assertEquals(expected, actual);
	}
}
