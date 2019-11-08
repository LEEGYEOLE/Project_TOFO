package com.notice;

import java.util.List;

public interface NoticeDAO {
	
	public int insertNotice(NoticeDTO dto); //�� ����ϱ�
	public int dataCount(); 
	public int dataCount(String condition, String keyword);
	public List<NoticeDTO> listNotice(int offset, int rows); //�Խù� ����Ʈ 
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword);
	public NoticeDTO readBoard(int num);
	public List<NoticeDTO> listNotice();
	public NoticeDTO preReadNotice(int num, String condition, String keyword);  //������
	public NoticeDTO nextReadNotice(int num, String condition, String keyword); //������
	public void updateHitCount(int num);
	public void updateNotice(NoticeDTO dto); //�� �����ϱ�
	public void deleteNotice(int num, String userId);  //�� �����ϱ�
	
	
	
	
	
	

}
