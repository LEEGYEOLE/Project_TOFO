package com.notice;

import java.util.List;

public interface NoticeDAO {
	
	public int insertNotice(NoticeDTO dto); //글 등록하기
	public int dataCount(); 
	public int dataCount(String condition, String keyword);
	public List<NoticeDTO> listNotice(int offset, int rows); //게시물 리스트 
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword);
	public NoticeDTO readBoard(int num);
	public List<NoticeDTO> listNotice();
	public NoticeDTO preReadNotice(int num, String condition, String keyword);  //이전글
	public NoticeDTO nextReadNotice(int num, String condition, String keyword); //다음글
	public void updateHitCount(int num);
	public void updateNotice(NoticeDTO dto); //글 수정하기
	public void deleteNotice(int num, String userId);  //글 삭제하기
	
	
	
	
	
	

}
