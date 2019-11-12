package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 개수
	public int dataCount(int num, String userId, String condition) {

		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();


		try {
			
			sb.append("  select count(s.sche_num)");
			sb.append("  from schedule s left outer join (");
			sb.append("     select sche_num, count(sche_num) cnt from attendance where userid=? group by  sche_num");
			sb.append("   ) a on s.sche_num=a.sche_num");
			sb.append("  where  s.num=? and sdate <= to_date(sysdate, 'YY/MM/DD')");
			
			if (condition.equalsIgnoreCase("att")) {
				sb.append("");

			}

			
			else if (condition.equalsIgnoreCase("attend")) {
				sb.append("  and cnt = 1");


			}
			
			else if (condition.equalsIgnoreCase("notattend")) {
				sb.append("  and cnt is null");


			}
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt(1);

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

		return result;
	}
	
	//데이터 갯수 (한 일정당 후기를 세는)
	public int dataCount(int groupNum, int schedule_num) {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select nvl(count(*),0)");
			sb.append("  from review r join schedule s");
			sb.append("  on r.sche_num =s.sche_num");
			sb.append("  join member m");
			sb.append("  on r.userid =m.userid");
			sb.append("  where num = ? and s.sche_num =?");
			sb.append(" group by num,s.sche_num");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, schedule_num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		
		
		
		return result;
	}

	// 파라미터 num=>그룹번호 , 후기게시판에 기간이 지나면 게시물 뿌려주기~
	public List<ReviewDTO> reviewlist(int num, int offset, int rows, String userId) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append("  select s.sche_num, to_char(sdate, 'YY/MM/DD'), to_char(edate, 'YY/MM/DD'), title, addr,content, nvl(cnt,0) cnt, s.num ,peo");
			sb.append("  from schedule s left outer join (");
			sb.append("   select sche_num, count(sche_num) cnt from attendance where userid=? group by  sche_num");
			sb.append("     ) a on s.sche_num=a.sche_num");
			sb.append("  join (select count(*) peo ,sche_num from attendance group by sche_num) t");
			sb.append("  on s.sche_num =t.sche_num");
			sb.append("     where  s.num=? and sdate <= to_date(sysdate, 'YY/MM/DD') ");
			sb.append("  order by s.sche_num desc");
			sb.append("  offset ? rows fetch first ? rows only");
	
		
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setInt(2, num);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setReviewNum(rs.getInt(1));
				dto.setStartDate(rs.getString(2));
				dto.setEndDate(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setLocation(rs.getString(5));
				dto.setContentDetail(rs.getString(6));		
				dto.setAttendCheck(rs.getInt(7));
				dto.setGroupNum(rs.getInt(8));
				dto.setPersonnel(rs.getInt(9));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		return list;
	}

	
	// 후기게시판에 기간이 지나면 게시물 뿌려주기~
	public List<ReviewDTO> reviewlist(int num, int offset, int rows, String condition, String userid) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append("  select s.sche_num, to_char(sdate, 'YY/MM/DD'), to_char(edate, 'YY/MM/DD'), title, addr,content, nvl(cnt,0) cnt, s.num ,peo");
			sb.append("  from schedule s left outer join (");
			sb.append("   select sche_num, count(sche_num) cnt from attendance where userid=? group by  sche_num");
			sb.append("     ) a on s.sche_num=a.sche_num");
			sb.append("  join (select count(*) peo ,sche_num from attendance group by sche_num) t");
			sb.append("  on s.sche_num =t.sche_num");
			sb.append("     where  s.num=? and sdate <= to_date(sysdate, 'YY/MM/DD') ");

			
			if(condition.equalsIgnoreCase("att")) {
				sb.append("");
			}
			
			else if (condition.equalsIgnoreCase("attend")) {
				sb.append("  and cnt = 1");

			}

			else if (condition.equalsIgnoreCase("notattend")) {
				sb.append("  and cnt is null");
				
			}


			sb.append("  order by s.sche_num desc");
			sb.append("  offset ? rows fetch first ? rows only");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userid);
			pstmt.setInt(2, num);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setReviewNum(rs.getInt(1));
				dto.setStartDate(rs.getString(2));
				dto.setEndDate(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setLocation(rs.getString(5));
				dto.setContentDetail(rs.getString(6));		
				dto.setAttendCheck(rs.getInt(7));
				dto.setGroupNum(rs.getInt(8));
				dto.setPersonnel(rs.getInt(9));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		return list;
	}
	
	
	public ReviewDTO review(int num) {
		ReviewDTO dto = new ReviewDTO();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql;
		
		try {
			sql = "select to_char(sdate,'YY/MM/DD'),content,addr from schedule where sche_num =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setStartDate(rs.getString(1));
				dto.setContentDetail(rs.getString(2));
				dto.setLocation(rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return dto;
	}
	
	public List<ReviewDTO> guestbooklist(int schedule_num, int groupnum){
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		try {

			sb.append("select s.num, s.sche_num, r.content ,m.username,r.created,r.rev_num, r.userid ");
			sb.append(" from review r join schedule s");
			sb.append(" on r.sche_num =s.sche_num");
			sb.append(" join member m");
			sb.append(" on r.userid =m.userid");
			sb.append(" where num = ? and s.sche_num =?");
			sb.append("  order by r.created desc");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, groupnum);
			pstmt.setInt(2, schedule_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setStartDate(rs.getString(1));
				dto.setScheNum(rs.getInt(2));
				dto.setContentDetail(rs.getString(3));
				dto.setUserName(rs.getString(4));
				dto.setCreated(rs.getString(5));
				dto.setReviewNum(rs.getInt(6));
				dto.setUserId(rs.getString(7));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		return list;
	}
	
	public void insertreview(ReviewDTO dto) {
		PreparedStatement pstmt=null;
		String sql;

		
		try {
			sql = "insert into review (rev_num , sche_num ,userid,content) values (rev_seq.nextval ,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getReviewNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContentDetail());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
	public void insertphoto(ReviewDTO dto) {
		PreparedStatement pstmt=null;
		String sql;
		
		//진 테이블에 사진들 추가 
		try {
			sql = "insert into picture(pic_num, pictureFilename,rev_Num ) values(pic_seq.nextval , ? ,  rev_seq.currval)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getImageFilename());
			
			
			pstmt.executeUpdate();
		} catch (Exception e) {
		
			
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public List<ReviewDTO> photolist(int schedule_num){
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "select  picturefilename ,userid from picture p join review r on p.rev_num = r.rev_num where sche_num =?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, schedule_num);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setImageFilename(rs.getString(1));
				dto.setUserId(rs.getString(2));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		
		return list;
			
	}

	public List<ReviewDTO> personlist(int schedule_num){
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "select m.userid, tel,username from attendance a join member m on a.userid =m.userid where sche_num =? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, schedule_num);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setUserId(rs.getString(1));
				dto.setTel(rs.getString(2));
				dto.setUserName(rs.getString(3));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		
		return list;
			
	}
	
	public ReviewDTO readMember(int num) {
		ReviewDTO dto = new ReviewDTO();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		
		try {
			
			sql = "select userid from review where rev_num= ?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setUserId(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return dto;
	}
	
	public void deletereview(int reviewnum) {
		PreparedStatement pstmt=null;
		String sql;

		
		try {
			sql = "delete from review where rev_num=? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewnum);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
	public  List<ReviewDTO> readphotofile(int reviewnum) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		String sql;

		
		try {
			sql = "select picturefilename from review r join picture p on r.rev_num =p.rev_num where r.rev_num=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewnum);
			
			rs =pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setImageFilename(rs.getString(1));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return list;
		
	}
	
	public void deletePhoto(int reviewnum) {
		PreparedStatement pstmt=null;
		String sql;

		
		try {
			sql = "delete from picture where rev_num=? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, reviewnum);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
    public int updateReview(ReviewDTO dto) {
    	int result=0;
    	PreparedStatement pstmt=null;
    	String sql;
    	
    	try {
			sql = "UPDATE review SET content=?  WHERE rev_num = ? AND userId = ? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getContentDetail());
			pstmt.setInt(2, dto.getReviewNum());
			pstmt.setString(3, dto.getUserId());
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
	
}
