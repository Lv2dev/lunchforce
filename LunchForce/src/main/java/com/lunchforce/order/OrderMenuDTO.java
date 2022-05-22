package com.lunchforce.order;

public class OrderMenuDTO {
	private int id;
	private int orderlistId;
	private int menuId;
	private String menuName;
	private int price;
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderlistId() {
		return orderlistId;
	}
	public void setOrderlistId(int orderlistId) {
		this.orderlistId = orderlistId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
