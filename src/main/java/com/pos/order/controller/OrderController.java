package com.pos.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pos.order.entity.Order;
import com.pos.order.service.OrderService;


@RestController
public class OrderController {
	
	private static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> saveOrder(@RequestBody Order order) throws Exception{
		log.info("Payload - "+order);
			return new ResponseEntity<>(orderService.saveOrder(order),HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> updateOrder(@RequestBody Order order) throws Exception{
		log.info("Payload - "+order);
		return new ResponseEntity<>(orderService.updateOrder(order),HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/orderList", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Order> fetchOrderList(){
		return orderService.fetchOrderList();
	}
	@GetMapping(value = "/order/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Order getOrder(@PathVariable long orderId) throws Exception{
		Order order=orderService.getOrder(orderId);
		if(order==null) {
			throw new Exception("Order not found -"+orderId);
		}
		return order;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleException(
	        Exception e) throws IOException {
	    Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("message", e.getMessage());
	    errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());

	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
}
