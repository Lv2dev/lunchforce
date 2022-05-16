package com.lunchforce.store;

public class StoreAddressDTO {
	private int id;
	private int store_id;
	private String address;
	private double addressX;
	private double addressY;
	
	//getters and setters
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getAddressX() {
		return addressX;
	}
	public void setAddressX(double addressX) {
		this.addressX = addressX;
	}
	public double getAddressY() {
		return addressY;
	}
	public void setAddressY(double addressY) {
		this.addressY = addressY;
	}
	
	
	
	
}
