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
	
	public void insertTeam(TeamDTO dto) {
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();

		sb.append("INSERT ALL ");
		sb.append("INTO TEAM (num, title, content, userCount,imageFilename,userId) values( ");
		sb.append("			team_seq.nextval, ?,?,?,?,? ) ");
		sb.append("INTO TEAMLIST (LISTNUM,USERID,RANK,NUM) values( ");
		sb.append("			LIST_SEQ.nextval, ?, '0',team_seq.currval ) ");
		sb.append("select * from DUAL ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getUserCount());
			pstmt.setString(4, dto.getImageFilename());
			pstmt.setString(5, dto.getUserId());
			pstmt.setString(6, dto.getUserId());
			
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public List<TeamDTO> listTeam(String userId){
		List<TeamDTO> list = new ArrayList<TeamDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		try {
			//select t.num, title, content, userCount, t.userId from team t
//			left outer join teamlist tl on t.num = tl.num
//					where tl.userid='ccc';
			sb.append("SELECT t.num, title, t.userId, imageFilename ");
			sb.append("FROM Team t ");
			sb.append("LEFT OUTER JOIN teamlist tl ON t.num = tl.num ");
			sb.append("WHERE tl.userid=? ");
			sb.append("ORDER BY TITLECREATED DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TeamDTO dto=new TeamDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setUserId(rs.getString("userId"));
				dto.setImageFilename(rs.getString("imageFilename"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
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
		return list;
	}
//	
	public TeamDTO readTeam(int num) {
		TeamDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select num, title, content, userCount from team where num=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new TeamDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setUserCount(rs.getInt("userCount"));
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
	public void updateTeam(TeamDTO dto) {
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();

		sb.append("UPDATE TEAM ");
		sb.append("SET title=?, content=?, userCount=? ");
		sb.append("WHERE num=?" );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getUserCount());
			pstmt.setInt(4, dto.getNum());
			
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
