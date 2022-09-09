package com.pos.order.service;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private OrderRepository or;
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public Order saveOrder(Order order) {
		calculatingTotalPrice(order);
		calculatingDiscount(order);
		order.setProductIdList(order.getProductIdList());
		return or.save(order);
	}

	@Override
	public Order updateOrder(Order order) {
		calculatingDiscount(order);
		return or.save(order);
	}
	
	private Products[] fetchProductList(){
		System.out.println("rest called");
		org.springframework.http.HttpHeaders hs = new org.springframework.http.HttpHeaders();
		hs.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(hs);
		ResponseEntity<Products[]> response = restTemplate.exchange("http://localhost:8080/productList", HttpMethod.GET,
				entity, Products[].class);
		System.out.println(response.getBody());
		return response.getBody();//(List<Products>) restTemplate.getForObject("http://localhost:8080/productList",Products.class);
	}
	
	private void calculatingTotalPrice(Order order) {
		double totalPrice = 0; 
		List<Integer> productIdList = Arrays.asList(order.getProductIdList());
		List<Products> products = Arrays.asList(fetchProductList());
		System.out.println("----product id list from order - "+productIdList);
		System.out.println("----product id list from products - "+products);
		for(Integer l : productIdList) {
			for(Products product : products) {
				if(l == product.getProductID()) {
					totalPrice = totalPrice+product.getPrice();
				}
			}
		}
		System.out.println("totalPrice--->"+totalPrice);
		order.setTotalAmount(totalPrice);
	}
	
	private void calculatingDiscount(Order order) {
		double totalPrice = order.getTotalAmount(); 
		if(order.getSubscriptionId() != null) {
			if(order.getSubscriptionId().equals("SUP01")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*5/100;
			}else if(order.getSubscriptionId().equals("SUP02")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*10/100;
			}else if(order.getSubscriptionId().equals("SUP03")) {
				totalPrice = order.getTotalAmount()-order.getTotalAmount()*15/100;
			}
		}		
		System.out.println("totalPrice discount--->"+totalPrice);
		order.setTotalAmount(totalPrice);
	}

	@Override
	public List<Order> fetchOrderList() {
		return (List<Order>) or.findAll();
	}

}
