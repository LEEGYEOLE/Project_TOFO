package com.team;

public class TeamDTO {
	private int num;	//팀 번호
	private String userId; //유저아이디 - 모임장아이디
	private String title; //모임 이름
	private String content; //상세정보
	private int userCount;	//인원수 (정원)
	private String titleCreated; //생성일
	private String imageFilename; //이미지파일이름
	
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
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getTitleCreated() {
		return titleCreated;
	}
	public void setTitleCreated(String titleCreated) {
		this.titleCreated = titleCreated;
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	@Override
	public String toString() {
		return "TeamDTO [num=" + num + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", userCount=" + userCount + ", titleCreated=" + titleCreated + ", imageFilename=" + imageFilename
				+ "]";
	}
	
}
