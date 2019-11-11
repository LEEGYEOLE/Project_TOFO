package com.notice;

import java.util.List;

public interface NoticeDAO {
	
	public int insertNotice(NoticeDTO dto); //�� ����ϱ�
	public int dataCount(int groupNum); 
	public int dataCount(String condition, String keyword , int groupNum);
	public List<NoticeDTO> listNotice(int offset, int rows,int groupNum); //�Խù� ����Ʈ 
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword, int groupNum);
	public List<NoticeDTO> listNotice(int groupNum);
	public NoticeDTO readNotice(int num);
	public NoticeDTO preReadNotice(int num, String condition, String keyword, int groupNum);  //������
	public NoticeDTO nextReadNotice(int num, String condition, String keyword, int groupNum); //������
	public void updateHitCount(int num);
	public void updateNotice(NoticeDTO dto); //�� �����ϱ�
	public void deleteNotice(int num, String userId);  //�� �����ϱ�
}
