package com.team;

public class TeamDTO {
	private int num;	//�� ��ȣ
	private String userId; //�������̵� - ��������̵�
	private String title; //���� �̸�
	private String content; //������
	private int userCount;	//�ο��� (����)
	private String titleCreated; //������
	private String imageFilename; //�̹��������̸�
	
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
