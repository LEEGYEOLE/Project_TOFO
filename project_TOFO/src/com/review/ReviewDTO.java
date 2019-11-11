package com.review;

public class ReviewDTO {
 private int scheNum;//일정번호
 private int reviewNum;//번호
 private int listNum;//리스트 뿌릴 떄 번호!!
 private int groupNum; //그룹번호
 private String title;//일정 제목
 private String contentDetail;//일정 상세일정(내용)
 private String location;//일정 위치
 private String startDate;//일정 시작날짜
 private String endDate;//일장 끝날날짜
 private String created;//일정 작성일
 private int personnel;//일정 참여인원
 private int attendCheck;//일정 참여 여부
 private String lat; //일정 위치 위도
 private String tel;
 
 private String saveFilename;
 private String originalFilename;
 private long filesize;
 private String imageFilename;
 
 
 
 public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public int getScheNum() {
	return scheNum;
}
public void setScheNum(int scheNum) {
	this.scheNum = scheNum;
}
public int getGroupNum() {
	return groupNum;
}
public void setGroupNum(int groupNum) {
	this.groupNum = groupNum;
}
public int getListNum() {
	return listNum;
}
public void setListNum(int listNum) {
	this.listNum = listNum;
}
private String lon; //일정 위치 경도
 
 //후기
 private String userId; //유저아이디;
 private String userName; //유저이름
 private String reviewContent;// 후기내용
 private String reviewCreated;//후기 작성일자
 
 
 
 

public String getImageFilename() {
	return imageFilename;
}
public void setImageFilename(String imageFilename) {
	this.imageFilename = imageFilename;
}
public String getSaveFilename() {
	return saveFilename;
}
public void setSaveFilename(String saveFilename) {
	this.saveFilename = saveFilename;
}
public String getOriginalFilename() {
	return originalFilename;
}
public void setOriginalFilename(String originalFilename) {
	this.originalFilename = originalFilename;
}
public long getFilesize() {
	return filesize;
}
public void setFilesize(long filesize) {
	this.filesize = filesize;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public int getReviewNum() {
	return reviewNum;
}
public void setReviewNum(int reviewNum) {
	this.reviewNum = reviewNum;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContentDetail() {
	return contentDetail;
}
public void setContentDetail(String contentDetail) {
	this.contentDetail = contentDetail;
}


public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getCreated() {
	return created;
}
public void setCreated(String created) {
	this.created = created;
}
public int getPersonnel() {
	return personnel;
}
public void setPersonnel(int personnel) {
	this.personnel = personnel;
}
public int getAttendCheck() {
	return attendCheck;
}
public void setAttendCheck(int attendCheck) {
	this.attendCheck = attendCheck;
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
public String getReviewContent() {
	return reviewContent;
}
public void setReviewContent(String reviewContent) {
	this.reviewContent = reviewContent;
}
public String getReviewCreated() {
	return reviewCreated;
}
public void setReviewCreated(String reviewCreated) {
	this.reviewCreated = reviewCreated;
}
 
 

 
 

 
 
 
}
