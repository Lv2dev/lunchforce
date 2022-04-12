package com.lunchforce.store;

import java.sql.Date;
import java.sql.Timestamp;

//가게 정보를 가지고있는 클래스
public class StoreDTO {
	private int storeId;
	private String userId;
	private String category;
	private String notice;
	private int tel;
	private String thumb;
	private int openTime;
	private int closeTime;
	//쉬는 요일
	private int restDay;
	//쉬는시간 몇시부터:>
	private int braketimeStart;
	private int braketimeEnd;
	private Timestamp joinDay;
	
	
	//getters and setters
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public int getOpenTime() {
		return openTime;
	}
	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}
	public int getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}
	public int getRestDay() {
		return restDay;
	}
	public void setRestDay(int restDay) {
		this.restDay = restDay;
	}
	public int getBraketimeStart() {
		return braketimeStart;
	}
	public void setBraketimeStart(int braketimeStart) {
		this.braketimeStart = braketimeStart;
	}
	public int getBraketimeEnd() {
		return braketimeEnd;
	}
	public void setBraketimeEnd(int braketimeEnd) {
		this.braketimeEnd = braketimeEnd;
	}
	public Timestamp getJoinDay() {
		return joinDay;
	}
	public void setJoinDay(Timestamp joinDay) {
		this.joinDay = joinDay;
	}
	
	
}
