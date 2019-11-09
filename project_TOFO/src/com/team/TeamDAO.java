package com.team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<TeamDTO> readTeamMember(int num) {
		List<TeamDTO> list = new ArrayList<TeamDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select tl.userId, userName, birth, tel, rank " + 
					"from teamList tl " + 
					"left outer join member m on tl.userId=m.userId " + 
					"left outer join team t on tl.num=t.num where t.num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TeamDTO dto = new TeamDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				dto.setRank(rs.getString("rank"));
				
				list.add(dto);
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
		return list;		
	}
	
	public int dataCount(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM teamList where num=?;";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public void updateRank(TeamDTO dto) {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="update teamList set rank='모임원' where userId=? and num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getNum());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql="update teamList set rank='모임장' where userId=? and num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getNum());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public int deleteTeamList(TeamDTO dto, int num, String rank) {
    	int result=0;
    	PreparedStatement pstmt = null;
    	String sql;
    	
    	try {
    		if(rank.equals("모임장")) {
    			sql="delete from teamList where userId=?";
    			pstmt=conn.prepareStatement(sql);
    			pstmt.setString(1, dto.getUserId());
    		}
    		result = pstmt.executeUpdate();		
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
}
