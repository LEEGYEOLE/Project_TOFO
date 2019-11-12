package com.schedule;

public class ScheduleDTO {
	private int scheNum, num;
	private String userId, userName;
	private String title, content;
	private String sDate, eDate, stime, etime, created, period;
	private String addr, lat, lon;
	private int money;
	private String color;
	private String allDay;
	private int repeat, repeat_cycle;
	private int attend;
	
	public int getScheNum() {
		return scheNum;
	}
	public void setScheNum(int scheNum) {
		this.scheNum = scheNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAllDay() {
		return allDay;
	}
	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
	public int getRepeat() {
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	public int getRepeat_cycle() {
		return repeat_cycle;
	}
	public void setRepeat_cycle(int repeat_cycle) {
		this.repeat_cycle = repeat_cycle;
	}
	
	public int getAttend() {
		return attend;
	}
	public void setAttend(int attend) {
		this.attend = attend;
	}
	@Override
	public String toString() {
		return "ScheduleDTO [scheNum=" + scheNum + ", num=" + num + ", userId=" + userId + ", userName=" + userName
				+ ", title=" + title + ", content=" + content + ", sDate=" + sDate + ", eDate=" + eDate + ", stime="
				+ stime + ", etime=" + etime + ", created=" + created + ", period=" + period + ", addr=" + addr
				+ ", lat=" + lat + ", lon=" + lon + ", money=" + money + ", color=" + color + ", allDay=" + allDay
				+ ", repeat=" + repeat + ", repeat_cycle=" + repeat_cycle + ", attend=" + attend + "]";
	}
}
