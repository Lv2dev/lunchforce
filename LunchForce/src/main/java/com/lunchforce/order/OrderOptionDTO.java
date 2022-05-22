package com.lunchforce.order;

public class OrderOptionDTO {
	private int id;
	private int ordermenuId;
	private int optionId;
	private int price;
	private String optionName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrdermenuId() {
		return ordermenuId;
	}
	public void setOrdermenuId(int ordermenuId) {
		this.ordermenuId = ordermenuId;
	}
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
	
}
