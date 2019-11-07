package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO {
private Connection conn = DBConn.getConnection();
	
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT userid, username ,userpwd ,birth, email ,tel ");
			sb.append("       ,created_date, Modify_date, enabled ");
			sb.append("       FROM member ");
			sb.append("       WHERE userId=? AND enabled=1 ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				
				dto.setUserId(rs.getString(1));
				dto.setUserName(rs.getString(2));
				dto.setUserPwd(rs.getString(3));
				dto.setBirth(rs.getString(4));
				dto.setEmail(rs.getString(5));
				dto.setTel(rs.getString(6));
				dto.setCreated_date(rs.getString(7));
				dto.setModify_date(rs.getString(8));								
				dto.setEnabled(rs.getInt(9));
				

			
				
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return dto;
	}
}
