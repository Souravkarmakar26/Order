package com.pos.order.service;

import java.util.List;
import java.util.Optional;

import com.pos.order.entity.Order;

public interface OrderService {

	public Order saveOrder(Order order) throws Exception;
	public Order updateOrder(Order order) throws Exception;
	public List<Order> fetchOrderList();
	public Order getOrder(long orderId);
	
}
