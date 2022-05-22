package com.lunchforce.order;

import java.sql.Date;

public class OrderDTO {
	private int orderlistId;
	private String userId;
	private int storeId;
	private Date orderDate;
	private int price;
	private int status;
	private int time;
	
	
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
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
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
}
