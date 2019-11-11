package com.notice;

import java.util.List;

public interface NoticeDAO {
	
	public int insertNotice(NoticeDTO dto); //글 등록하기
	public int dataCount(int groupNum); 
	public int dataCount(String condition, String keyword , int groupNum); //게시물검색
	public List<NoticeDTO> listNotice(int offset, int rows,int groupNum); //게시물 리스트 , 검색이 아닐때
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword, int groupNum);
	public List<NoticeDTO> listNotice(int groupNum);  //공지글
	public NoticeDTO readNotice(int num);
	public NoticeDTO preReadNotice(int num, String condition, String keyword, int groupNum);  //이전글
	public NoticeDTO nextReadNotice(int num, String condition, String keyword, int groupNum); //다음글
	public void updateHitCount(int num); //조회수
	public void updateNotice(NoticeDTO dto); //글 수정하기
	public void deleteNotice(int num, String userId);  //글 삭제하기

}
