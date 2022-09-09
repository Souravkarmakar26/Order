package com.pos.order.service;

import java.util.List;

import com.pos.order.entity.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	public Order updateOrder(Order order);
	public List<Order> fetchOrderList();
	
}
