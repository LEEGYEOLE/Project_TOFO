package com.teamList;

public class TeamListDTO {
	private int listNum;	//ÆÀ ¸â¹ö ¸ñ·Ï ¹øÈ£
	private String userId;	//ÆÀ ¸â¹ö ¾ÆÀÌµğ
	private String signUpDate;	//°¡ÀÔÀÏ
	private String rank;	//µî±Ş
	private int num;	//ÆÀ ¹øÈ£
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSignUpDate() {
		return signUpDate;
	}
	public void setSignUpDate(String signUpDate) {
		this.signUpDate = signUpDate;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "TeamListDTO [listNum=" + listNum + ", userId=" + userId + ", signUpDate=" + signUpDate + ", rank="
				+ rank + ", num=" + num + "]";
	}
	
}
	
