package com.review;

public class ReviewDTO {
 private int scheNum;//������ȣ
 private int reviewNum;//��ȣ
 private int listNum;//����Ʈ �Ѹ� �� ��ȣ!!
 private int groupNum; //�׷��ȣ
 private String title;//���� ����
 private String contentDetail;//���� ������(����)
 private String location;//���� ��ġ
 private String startDate;//���� ���۳�¥
 private String endDate;//���� ������¥
 private String created;//���� �ۼ���
 private int personnel;//���� �����ο�
 private int attendCheck;//���� ���� ����
 private String lat; //���� ��ġ ����
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
private String lon; //���� ��ġ �浵
 
 //�ı�
 private String userId; //�������̵�;
 private String userName; //�����̸�
 private String reviewContent;// �ı⳻��
 private String reviewCreated;//�ı� �ۼ�����
 
 
 
 

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
