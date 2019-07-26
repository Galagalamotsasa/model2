package com.goodee.dto;
import java.sql.Date;

public class UserPointLog {
	private Date givendate; // 로그 기록일 
	private int pointval; // 몇 포인트
	private String why; // 포인트 가감 이유
	public UserPointLog(Date givendate, int pointval, String why) {
		this.givendate = givendate;
		this.pointval = pointval;
		this.why = why;
	}
	public Date getGivendate() {
		return givendate;
	}
	public void setGivendate(Date givendate) {
		this.givendate = givendate;
	}
	public int getPointval() {
		return pointval;
	}
	public void setPointval(int pointval) {
		this.pointval = pointval;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	@Override
	public String toString() {
		return "UserPointLog [givendate=" + givendate + ", pointval=" + pointval + ", why=" + why + "]";
	}
	
	
	
	
}
