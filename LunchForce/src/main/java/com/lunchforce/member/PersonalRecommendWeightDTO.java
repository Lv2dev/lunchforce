package com.lunchforce.member;

public class PersonalRecommendWeightDTO {
	private int id;
	private String userId;
	private int allergy;
	private int recommend_keyword;
	private int feeling;
	private int condition;
	private int health;
	private int weather;
	private int temperature;
	private int dust;
	private int humidity;
	private int favor;
	private int orderList;
	private int calorie;
	private int nutrition;
	private int category;
	private int score;
	private int distance; //거리의 가중치 - 거리를 얼마나 둘지는 따로 저장(address table)
	private int random;
	
	
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
	public int getAllergy() {
		return allergy;
	}
	public void setAllergy(int allergy) {
		this.allergy = allergy;
	}
	public int getRecommend_keyword() {
		return recommend_keyword;
	}
	public void setRecommend_keyword(int recommend_keyword) {
		this.recommend_keyword = recommend_keyword;
	}
	public int getFeeling() {
		return feeling;
	}
	public void setFeeling(int feeling) {
		this.feeling = feeling;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getWeather() {
		return weather;
	}
	public void setWeather(int weather) {
		this.weather = weather;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getDust() {
		return dust;
	}
	public void setDust(int dust) {
		this.dust = dust;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public int getFavor() {
		return favor;
	}
	public void setFavor(int favor) {
		this.favor = favor;
	}
	public int getOrderList() {
		return orderList;
	}
	public void setOrderList(int orderList) {
		this.orderList = orderList;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	
	
}
