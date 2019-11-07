package com.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class ScheduleDAO {
	private Connection conn = DBConn.getConnection();
	
	// 일정 등록하기.
	public int insertSchedule(ScheduleDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="insert into schedule(sche_Num, num, userId, sDate, eDate, title, content, addr, lat, lon, money, color) values(sche_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getsDate());
			pstmt.setString(4, dto.geteDate());
			pstmt.setString(5, dto.getTitle());
			pstmt.setString(6, dto.getContent());
			pstmt.setString(7, dto.getAddr());
			pstmt.setString(8, dto.getLat());
			pstmt.setString(9, dto.getLon());
			pstmt.setInt(10, dto.getMoney());
			pstmt.setString(11, dto.getColor());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	// 내가 속한 모임의 전체 일정 가져오기
		public ScheduleDTO readSchedule(String userId) { // 이래도 오류 안나는건가?
			ScheduleDTO dto= new ScheduleDTO();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sql;
				
			try {
				sql ="select s.sche_Num, t.num, tl.userId, s.sDate, s.eDate, s.created, s.title, s.color from team t join schedule s on t.num=s.num "; 
				sql+="join teamList tl on tl.num=t.num where tl.userId=?";		 
			 
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUserId());
				rs=pstmt.executeQuery();
				if(rs.next()) {
					dto = new ScheduleDTO();
					dto.setSche_Num(rs.getInt("s.sche_Num"));
					dto.setNum(rs.getInt("t.num"));
					dto.setUserId(rs.getString("tl.userId"));
					dto.setsDate(rs.getString("s.sDate"));
					dto.seteDate(rs.getString("s.eDate"));
					dto.setCreated(rs.getString("s.created"));
					dto.setTitle(rs.getString("s.title"));
					dto.setColor(rs.getString("s.color"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			return dto;
		}	
	
}
