package com.lunchforce.member;

public class PersonalMenuRecommendDTO {
	private int id;
	private String userId;
	private int favor;
	private int hate_favor;
	private int calorie;
	private int nutrition;
	private int category;
	private int allergy;
	private int distance;
	private String recommendKeyword;
	private String notRecommendKeyword;
	
	
	//getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getFavor() {
		return favor;
	}
	public void setFavor(int favor) {
		this.favor = favor;
	}
	public int getHate_favor() {
		return hate_favor;
	}
	public void setHate_favor(int hate_favor) {
		this.hate_favor = hate_favor;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public int getNutrition() {
		return nutrition;
	}
	public void setNutrition(int nutrition) {
		this.nutrition = nutrition;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getAllergy() {
		return allergy;
	}
	public void setAllergy(int allergy) {
		this.allergy = allergy;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getRecommendKeyword() {
		return recommendKeyword;
	}
	public void setRecommendKeyword(String recommendKeyword) {
		this.recommendKeyword = recommendKeyword;
	}
	public String getNotRecommendKeyword() {
		return notRecommendKeyword;
	}
	public void setNotRecommendKeyword(String notRecommendKeyword) {
		this.notRecommendKeyword = notRecommendKeyword;
	}
	
	
}
