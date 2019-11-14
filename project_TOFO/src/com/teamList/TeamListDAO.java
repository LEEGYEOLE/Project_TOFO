package com.teamList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class TeamListDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<HashMap<String, Object>> listTeamMember(int groupNum){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select listNum, tl.num, tl.userId, userName, to_char(birth,'YYYY-MM-DD') birth, tel, rank " + 
					"from teamList tl " + 
					"left outer join member m on tl.userId=m.userId " + 
					"left outer join team t on tl.num=t.num where t.num=? order by rank";

			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("listNum", rs.getInt("listNum"));
				map.put("num", rs.getInt("num"));
				map.put("userId", rs.getString("userId"));
				map.put("userName", rs.getString("userName"));
				map.put("birth", rs.getString("birth"));
				map.put("tel", rs.getString("tel"));
				map.put("rank", rs.getString("rank"));
				
				list.add(map);
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
		
	public List<Map<String, Object>> readMemberList(String userId) {
		List<Map<String, Object>> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select userId, userName, to_char(birth, 'yyyy-mm-dd') birth, to_char(created_Date, 'yyyy-mm-dd') created_Date from member where (instr(userId, ?)>=1) and enabled=1");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", rs.getString("userId"));
				map.put("userName", rs.getString("userName"));
				map.put("birth", rs.getString("birth"));
				map.put("created_Date", rs.getString("created_Date"));	
				
				list.add(map);
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
	
	/**
	 * 모임장 권한 위임 관련, rank update함수
	 * 0이 모임장, 1이 모임원
	 * @param leader	모임장이었던 유저 id
	 * @param userId	권한을 위임받을 유저 id
	 * @param num	팀 번호
	 * @return
	 */
	public int updateRank(String leader, String userId, int groupNum) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="update teamList set rank='1' where userId=? and num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, leader);
			pstmt.setInt(2, groupNum);
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql="update teamList set rank='0' where userId=? and num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, groupNum);
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql="update team set userId=? where num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, groupNum);
			
			result = pstmt.executeUpdate();
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
		return result;
	}
	
	public int deleteTeamList(String userId, int groupNum) {
    	int result=0;
    	PreparedStatement pstmt = null;
    	String sql;
    	
    	try {
    		sql="delete from teamList where userId=? and num=?";
    		pstmt=conn.prepareStatement(sql);
    		pstmt.setString(1, userId);
    		pstmt.setInt(2, groupNum);
    		
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
	
	public int insertTeamList(String userId, String rank, int groupNum){
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "insert into teamList(listNum, userId, rank, num) values(list_seq.nextval, ?, '1', ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, groupNum);
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
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
	
	public int dataCount(int groupNum) {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select nvl(count(*), 0) from teamList where num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			
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
	
	public int userCount(int groupNum) {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select userCount from team where num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			
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
}
