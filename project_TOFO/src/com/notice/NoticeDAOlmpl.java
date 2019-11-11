package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAOlmpl implements NoticeDAO {
	private Connection conn = DBConn.getConnection();

	@Override
	public int insertNotice(NoticeDTO dto) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "INSERT INTO notice(num,  userId, subject, content, saveFilename, originalFilename, filesize, notice, groupNum) VALUES (notice_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getSaveFilename());
			pstmt.setString(5, dto.getOriginalFilename());
			pstmt.setLong(6, dto.getFilesize());
			pstmt.setInt(7, dto.getNotice());
			pstmt.setInt(8, dto.getGroupNum());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return result;
	}

	@Override
	public int dataCount(int groupNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql;

		try {

			sql = "SELECT NVL(COUNT(*), 0) FROM notice WHERE groupNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			
			rs = pstmt.executeQuery();
			pstmt.setInt(1, groupNum);
			if (rs.next()) {
				result = rs.getInt(1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}

		}

		return result;

	}

	@Override
	public int dataCount(String condition, String keyword , int groupNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			// 조인안하면 이름 검색이 안된다.
			sql = "SELECT NVL(COUNT(*), 0) FROM notice b JOIN member m ON b.userId = m.userId WHERE groupNum = ? "; // 이름을 member이 가지고 있기
																									// 때문에
			if (condition.equalsIgnoreCase("created")) { // created이면~
				keyword = keyword.replace("-", ""); // condition은 컬럼명
				sql += " AND TO_CHAR(created, 'YYYYMMDD') = ?"; // 날짜검색
			} else if (condition.equalsIgnoreCase("userName")) {
				sql += " AND INSTR(userName, ?)=1 "; // 앞부분쪽 보겠다는 뜻
			} else {
				sql += " AND INSTR(" + condition + ",?) >=1 "; // condition은 컬럼명
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setString(2, keyword);

			rs = pstmt.executeQuery();

			if (rs.next()) { // 무조건해야함
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}

		}

		return result;

	}
	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, int groupNum) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append("SELECT num, userName, subject, hitCount, saveFilename,  ");
			sb.append("  	created  ");
			sb.append(" FROM notice b ");
			sb.append(" JOIN member m ON b.userId = m.userId  ");
			sb.append(" WHERE groupNum = ? ");
			sb.append(" ORDER BY num DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY  ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
				list.add(dto);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}

		}

		return list;

	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword , int groupNum) {

		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append("SELECT num, userName, subject, hitCount, saveFilename, ");
			sb.append("  	created  ");
			sb.append(" FROM notice b ");
			sb.append(" JOIN member m ON b.userId = m.userId  ");
			sb.append(" WHERE groupNum = ? ");

			if (condition.equalsIgnoreCase("created")) {
				keyword = keyword.replace("-", ""); // condition은 컬럼명
				sb.append("  AND TO_CHAR(created, 'YYYYMMDD') = ?  ");
				
			} else if (condition.equalsIgnoreCase("userName")) {
				sb.append(" AND INSTR(userName, ?)=1 "); // 앞부분쪽 보겠다는 뜻
			} else {
				sb.append(" AND INSTR(" + condition + ",?) >=1 ");// condition은 컬럼명
			} 

			sb.append(" ORDER BY num DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY  ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, groupNum);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}

		}

		return list;

	}
	
	
	
	

	@Override
	public List<NoticeDTO> listNotice(int groupNum) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT num, userName, subject, saveFilename,  ");
			sb.append("       hitCount, TO_CHAR(created, 'YYYY-MM-DD') created  ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId  ");
			sb.append(" WHERE notice=1 AND groupNum=? ");
			sb.append(" ORDER BY num DESC ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, groupNum);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setNum(rs.getInt("num"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));

				list.add(dto);
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

		return list;

	}
	
	@Override
	public NoticeDTO readNotice(int num) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		// 컬럼 다 나열함
		try {
			sb.append("SELECT num, n.userId, userName, subject, saveFilename,  originalFilename, ");
			sb.append("    content, hitCount, created , fileSize, notice  ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId  ");
			sb.append(" WHERE num=?  ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();

				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				dto.setFilesize(rs.getInt("filesize"));
				dto.setNotice(rs.getInt("notice"));

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
	

	@Override
	public NoticeDTO preReadNotice(int num, String condition, String keyword, int groupNum) {
		NoticeDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append("  SELECT num, subject FROM notice b JOIN member m ON b.userId = m.userId AND groupNum=? ");
				 if(condition.equalsIgnoreCase("created")) {
	                   keyword=keyword.replaceAll("-", "");
	                   sb.append(" AND (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
	                } else {
	                   sb.append(" AND (INSTR(" + condition + ", ?) >= 1)  ");
	                }
	                sb.append("         AND (num > ? ) ");
	                sb.append(" ORDER BY num ASC  ");
	                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, num);


			} else {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append("  SELECT num, subject FROM notice "); // 이건 조인안해도됨 , 이건 이름 검색을 안해도 되기 때문에
																	// 해도 되고 안해도 됨
				sb.append("     WHERE num > ? AND groupNum =? ");
				sb.append("         ORDER BY num ASC ");
				sb.append("      ) tb WHERE ROWNUM=1 ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, num);
				pstmt.setInt(2, groupNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}

		return dto;
	}
	@Override
	public NoticeDTO nextReadNotice(int num, String condition, String keyword, int groupNum) {
		NoticeDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append("  SELECT num, subject FROM notice b JOIN member m ON b.userId = m.userId  WHERE groupNum = ? ");
				 if(condition.equalsIgnoreCase("created")) {
	                   keyword=keyword.replaceAll("-", "");
	                   sb.append(" AND (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
	                } else {
	                   sb.append(" AND (INSTR(" + condition + ", ?) >= 1)  ");
	                }
	                sb.append("         AND (num < ? )  ");
	                sb.append(" ORDER BY num DESC  ");
	                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, num);

			} else {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append("  SELECT num, subject FROM notice ");
				sb.append("     WHERE groupNum = ? AND num < ? ");
				sb.append("         ORDER BY num DESC ");
				sb.append("      ) tb WHERE ROWNUM=1 ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setInt(2, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
		}

		return dto;
	}

	@Override
	public void updateHitCount(int num) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET hitCount=hitCount+1 WHERE num=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}


	@Override
	public void updateNotice(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET notice=?, subject=?, content=?, saveFilename=?, originalFilename=?, filesize=? ";
			sql += " WHERE num=? AND userId=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getSaveFilename());
			pstmt.setString(5, dto.getOriginalFilename());
			pstmt.setLong(6, dto.getFilesize());
			pstmt.setInt(7, dto.getNum());
			pstmt.setString(8, dto.getUserId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
		
	

	@Override
	public void deleteNotice(int num, String userId) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if(userId.equals("admin")) {
				sql = "DELETE FROM notice WHERE num = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				
			}else {
				sql = "DELETE FROM notice WHERE num = ? AND userId = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, userId);
			}
		
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}


}
