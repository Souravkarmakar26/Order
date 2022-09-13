package com.pos.order.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pos.order.entity.Order;
import com.pos.order.model.Products;
import com.pos.order.repository.OrderRepository;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
	
	@InjectMocks
    OrderServiceImpl orderServiceImpl;
	@Mock
	RestTemplate restTemplate;
	@Spy
	RestTemplate restTemplate1;
	@Mock
	OrderRepository orderRepository;
	/*
	 * @Mock Order order;
	 */
	
	@Test
	public  void saveOrder() {
		Order order = new Order();
		Products products = new Products();
		order.setOrderId((long) 100);
		order.setSubscriptionId("dummy");
		order.setTotalAmount(10000);
		Integer[] productIdList = {2, 1, 7, 6, 4, 2, 9};
		order.setProductIdList(productIdList);
		List<Integer> prodList = Arrays.asList(productIdList);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
		/*
		 * when(restTemplate.exchange(Mockito.any(), Mockito.any(),
		 * Mockito.any(),Mockito.any())) .thenReturn(responseEntity);
		 */
		List<String> prodctList = new ArrayList<>();
		prodctList.add("prod1");
		products.setPrice((double) 100);
		products.setProductID(12);
		products.setProductName("dummy");
		//products.setAdditionalProperty("dummy", prodctList);
		List<Products> productslst = Arrays.asList(products);
		Mockito.when(orderServiceImpl.saveOrder(order)).thenReturn(order);
		orderServiceImpl.saveOrder(order);
		//assertEquals(null, null);
	
		
	}
	@Test
	public  void updateOrder() {
		Order order = new Order();
		Products products = new Products();
		order.setOrderId((long) 100);
		order.setSubscriptionId("SUP01");
		order.setTotalAmount(10000);
		double totalPrice = order.getTotalAmount(); 
		Mockito.when(orderServiceImpl.updateOrder(order)).thenReturn(order);
		orderServiceImpl.updateOrder(order);
	}

}

