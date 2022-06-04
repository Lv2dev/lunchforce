package com.lunchforce.order;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderDTO {
	private int orderlistId;
	private String userId;
	private int storeId;
	private Timestamp orderDate;
	private int price;
	private int status;
	private int time;
	private String storeName;
	
	
	public int getOrderlistId() {
		return orderlistId;
	}
	public void setOrderlistId(int orderlistId) {
		this.orderlistId = orderlistId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
}
