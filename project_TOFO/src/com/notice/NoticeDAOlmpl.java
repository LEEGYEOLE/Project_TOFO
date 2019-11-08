package com.notice;

import java.util.List;

public class NoticeDAOlmpl implements NoticeDAO {

	@Override
	public int insertNotice(NoticeDTO dto) {
		
		return 0;
	}

	@Override
	public int dataCount() {
		
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		
		return 0;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		
		return null;
	}

	@Override
	public NoticeDTO readBoard(int num) {
		
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice() {
		
		return null;
	}

	@Override
	public NoticeDTO preReadNotice(int num, String condition, String keyword) {
		
		return null;
	}

	@Override
	public NoticeDTO nextReadNotice(int num, String condition, String keyword) {
		
		return null;
	}

	@Override
	public void updateHitCount(int num) {
		
		
	}

	@Override
	public void updateNotice(NoticeDTO dto) {
		
		
	}

	@Override
	public void deleteNotice(int num, String userId) {
		
		
	}

}
