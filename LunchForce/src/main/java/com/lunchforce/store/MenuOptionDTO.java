package com.lunchforce.store;

public class MenuOptionDTO {
	private int id;
	private int menuId;
	private String optionName;
	private int optionNumber;
	private int price;
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public int getOptionNumber() {
		return optionNumber;
	}
	public void setOptionNumber(int optionNumber) {
		this.optionNumber = optionNumber;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
