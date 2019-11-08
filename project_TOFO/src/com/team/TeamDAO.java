package com.team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class TeamDAO {
	private Connection conn = DBConn.getConnection();
	
	public TeamDTO readMember(String userId) {
		TeamDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select userId, userName, birth, created_Date from member where (instr(userId, ?)>=1) and enabled=1");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new TeamDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserId(rs.getString("userName"));
				dto.setUserId(rs.getString("birth"));
				dto.setUserId(rs.getString("created_Date"));
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
	
	public TeamDTO readTeamMember(int num) {
		TeamDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select tl.userId, userName, t.num, birth, tel, rank " + 
					"from teamList tl " + 
					"left outer join member m on tl.userId=m.userId " + 
					"left outer join team t on tl.num=t.num where t.num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new TeamDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserId(rs.getString("userName"));
				dto.setUserId(rs.getString("birth"));
				dto.setUserId(rs.getString("created_Date"));
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
