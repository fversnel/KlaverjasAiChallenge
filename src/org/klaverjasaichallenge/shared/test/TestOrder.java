
package org.klaverjasaichallenge.shared.test;

import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import java.util.LinkedList;

import org.klaverjasaichallenge.shared.Order;

public class TestOrder {

	@Test
	public void testSort() {
		List<Order> testOrders = new LinkedList<Order>();
		testOrders.add(new Order(5));
		testOrders.add(new Order(3));
		testOrders.add(new Order(1));
		testOrders.add(new Order(8));

		List<Order> actualOrders = Order.sort(testOrders);

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
}
