package com.pos.order.entity;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BillingOrder")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	private Integer[] productIdList;
	private String subscriptionId;
	private double totalAmount;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer[] getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(Integer[] productIdList) {
		this.productIdList = productIdList;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", productIdList=" + Arrays.toString(productIdList) + ", subscriptionId="
				+ subscriptionId + ", totalAmount=" + totalAmount + "]";
	}
	
	
}
