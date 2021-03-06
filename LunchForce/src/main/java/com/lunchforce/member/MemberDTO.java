package com.lunchforce.member;

import java.sql.Date;
import java.sql.Timestamp;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String tel;
	private String email;
	private Date bDay;
	private Date jDay;
	private int type; //관리자0 사업자1 일반2
	private int gender; //남자0 여자1
	private String question;
	private String answer;
	private String address;
	private double addressX;
	private double addressY;
	
	
	//getters and setters;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getbDay() {
		return bDay;
	}
	public void setbDay(Date bDay) {
		this.bDay = bDay;
	}
	public Date getjDay() {
		return jDay;
	}
	public void setjDay(Date jDay) {
		this.jDay = jDay;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
