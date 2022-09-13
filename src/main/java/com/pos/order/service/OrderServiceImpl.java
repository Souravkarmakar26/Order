package com.pos.order.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pos.order.entity.Order;
import com.pos.order.model.Products;
import com.pos.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final String subscriptionIdList[]= {"SUP01","SUP02","SUP03"};

	@Autowired
	private OrderRepository or;
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public Order saveOrder(Order order) throws Exception {
		calculatingTotalPrice(order);
		calculatingDiscount(order);
		log.info("Final order - "+order);
		return or.save(order);
	}

	@Override
	public Order updateOrder(Order order) throws Exception {
		String subscriptionId=order.getSubscriptionId();
		if(null==subscriptionId)
			throw new Exception("Please provide SubscriptionId");
		if(!Arrays.asList(subscriptionIdList).contains(subscriptionId))
			throw new Exception("Invalid SubscriptionId -" +subscriptionId);
		Order fetchOrder=getOrder(order.getOrderId());
		if(fetchOrder==null)
			throw new Exception("Order not found -"+order.getOrderId());
		fetchOrder.setSubscriptionId(subscriptionId);
		calculatingDiscount(fetchOrder);
		log.info("Final order update - "+fetchOrder);
		return or.save(fetchOrder);
	}
	
	private Products[] fetchProductList(){
		log.info("Rest call to Product for product list");
		org.springframework.http.HttpHeaders hs = new org.springframework.http.HttpHeaders();
		hs.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(hs);
		ResponseEntity<Products[]> response = restTemplate.exchange("http://localhost:8080/productList", HttpMethod.GET,
				entity, Products[].class);
		log.info("Rest call to Product Response "+response.getBody());
		return response.getBody();
	}
	
	private void calculatingTotalPrice(Order order) throws Exception {
		
		log.info("calculating total price");
		double totalPrice = 0; 
		boolean productFound=true;
		List<Integer> productIdList = Arrays.asList(order.getProductIdList());
		List<Products> products = Arrays.asList(fetchProductList());
		for(Integer l : productIdList) {
			for(Products product : products) {
				if(l == product.getProductID()) {
					totalPrice = totalPrice+product.getPrice();
					productFound=true;
					break;
				}
				else {
					productFound=false;
				}
			}
			if(!productFound)
				throw new Exception("Invalid Product Id -"+l);
		}
		order.setTotalAmount(totalPrice);
		log.info("calculating total price end");
		
	}
	
	private void calculatingDiscount(Order order) throws Exception {
		double totalPrice = order.getTotalAmount(); 
		if(order.getSubscriptionId() != null) {
			if(!Arrays.asList(subscriptionIdList).contains(order.getSubscriptionId()))
				throw new Exception("Invalid SubscriptionId -" +order.getSubscriptionId());
			if(order.getSubscriptionId().equals("SUP01")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*5/100;
			}else if(order.getSubscriptionId().equals("SUP02")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*10/100;
			}else if(order.getSubscriptionId().equals("SUP03")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*15/100;
			}
		}
		order.setTotalAmount(totalPrice);
	}

	@Override
	public List<Order> fetchOrderList() {
		return (List<Order>) or.findAll();
	}

	@Override
	public Order getOrder(long orderId) {
		// TODO Auto-generated method stub
		Order order;
		order=or.findById(orderId).orElse(null);
		return order;
	}

}

