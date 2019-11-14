package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class BoardDAO {
	private Connection conn=DBConn.getConnection();
	
	// 데이터 추가
	public int insertBoard(BoardDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "INSERT INTO bbs (num, userId, subject, content, teamNum) VALUES (bbs_seq.nextval, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getTeamNum());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		} 
		
		return result;
	}
	
	// 데이터 개수
	public int dataCount(int teamNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM bbs WHERE teamNum = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, teamNum);
			
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

	// 검색에서의 데이터 개수
	public int dataCount(String condition, String keyword, int teamNum) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0)  FROM bbs b JOIN member m ON b.userId=m.userId WHERE teamNum = ?";
			if(condition.equals("userName")) {
				sql += "  AND INSTR(userName, ?) = 1 ";
			} else if(condition.equals("created")) {
				keyword=keyword.replaceAll("-", "");
				sql += "  AND TO_CHAR(created, 'YYYYMMDD') = ? ";
			} else {
				sql += "  AND INSTR(" + condition+ ", ?) >= 1 ";
			}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, teamNum);
			pstmt.setString(2, keyword);
			
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
	
	// 게시물 리스트
	public List<BoardDTO> listBoard(int offset, int rows, int teamNum) {
		List<BoardDTO> list=new ArrayList<BoardDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT b.num, userName, subject, hitCount,  ");
			sb.append("       TO_CHAR(created, 'YYYY-MM-DD') created, ");
			sb.append("       NVL(replyCount, 0) replyCount ");
			sb.append(" FROM bbs b  ");
			sb.append(" JOIN member m ON b.userId = m.userId ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("      SELECT num, COUNT(*) replyCount FROM bbsReply WHERE answer=0 ");
			sb.append("      GROUP BY num");
			sb.append(" ) c ON b.num = c.num");
			sb.append(" WHERE teamNum = ? ");
			sb.append(" ORDER BY num DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, teamNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto=new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				dto.setReplyCount(rs.getInt("replyCount"));
				
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
	
	// 키워드 검색을 통해 보여지는 리스트 
	public List<BoardDTO> listBoard(int offset, int rows, String condition, String keyword, int teamNum) {
		List<BoardDTO> list=new ArrayList<BoardDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT b.num, userName, subject, hitCount,  ");
			sb.append("       TO_CHAR(created, 'YYYY-MM-DD') created, ");
			sb.append("       NVL(replyCount, 0) replyCount ");
			sb.append(" FROM bbs b  ");
			sb.append(" JOIN member m ON b.userId = m.userId  ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("      SELECT num, COUNT(*) replyCount FROM bbsReply WHERE answer=0 ");
			sb.append("      GROUP BY num");
			sb.append(" ) c ON b.num = c.num");
			sb.append(" WHERE teamNum = ? ");
			
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("-", "");
				sb.append(" AND TO_CHAR(created, 'YYYYMMDD') = ?");
			} else if(condition.equalsIgnoreCase("userName")) {
				sb.append(" AND INSTR(userName, ?) = 1 ");
			} else {
				sb.append(" AND INSTR("+condition+", ?) >= 1 ");
			}
			
			sb.append(" ORDER BY num DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, teamNum);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto=new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				dto.setReplyCount(rs.getInt("replyCount"));
				
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
	
	// 조회수 증가하기
	public int updateHitCount(int num)  {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE bbs SET hitCount=hitCount+1  WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
	
	// 해당 게시물 보기
	public BoardDTO readBoard(int num) {
		BoardDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT num, b.userId, userName, subject, content");
			sb.append("   ,created, hitCount ");
			sb.append(" FROM bbs b JOIN member m ON b.userId=m.userId  ");
			sb.append(" WHERE num = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
			}
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
		
		return dto;
	}
	
    // 이전글
    public BoardDTO preReadBoard(int num, String condition, String keyword, int teamNum) {
        BoardDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(keyword!=null && keyword.length() != 0) {
                sb.append("SELECT num, subject FROM bbs b JOIN member m ON b.userId = m.userId ");
                sb.append(" WHERE teamNum = ? ");
                if(condition.equals("userName")) {
                    sb.append(" AND ( INSTR(userName, ?) = 1)  ");
                } else if(condition.equals("created")) {
                	keyword=keyword.replaceAll("-", "");
                    sb.append(" AND (TO_CHAR(created, 'YYYYMMDD') = ?) ");
                } else {
                    sb.append(" AND ( INSTR("+condition+", ?) > 0) ");
                }
                sb.append("            AND (num > ? ) ");
                sb.append(" ORDER BY num ASC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, teamNum);
                pstmt.setString(2, keyword);
               	pstmt.setInt(3, num);
            } else {
                sb.append("SELECT num, subject FROM bbs ");
                sb.append(" WHERE num > ? AND teamNum = ? ");
                sb.append(" ORDER BY num ASC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
                pstmt.setInt(2, teamNum);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BoardDTO();
                dto.setNum(rs.getInt("num"));
                dto.setSubject(rs.getString("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
                
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
    
        return dto;
    }

    // 다음글
    public BoardDTO nextReadBoard(int num, String condition, String keyword, int teamNum) {
        BoardDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(keyword!=null && keyword.length() != 0) {
                sb.append("SELECT num, subject FROM bbs b JOIN member m ON b.userId=m.userId ");
                sb.append(" WHERE teamNum = ? ");
                if(condition.equals("userName")) {
                    sb.append(" AND ( INSTR(userName, ?) = 1) ");
                } else if(condition.equals("created")) {
                	keyword=keyword.replaceAll("-", "");
                    sb.append(" AND (TO_CHAR(created, 'YYYYMMDD') = ?) ");
                } else {
                    sb.append(" AND ( INSTR("+condition+", ?) > 0) ");
                }
                sb.append("          AND (num < ? ) ");
                sb.append(" ORDER BY num DESC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, teamNum);
                pstmt.setString(2, keyword);
               	pstmt.setInt(3, num);
            } else {
                sb.append("SELECT num, subject FROM bbs ");
                sb.append(" WHERE num < ? AND teamNum = ? ");
                sb.append(" ORDER BY num DESC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
                pstmt.setInt(2, teamNum);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BoardDTO();
                dto.setNum(rs.getInt("num"));
                dto.setSubject(rs.getString("subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
                
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }

        return dto;
    }
	
	// 게시물 수정
	public void updateBoard(BoardDTO dto) {
		PreparedStatement pstmt = null;
		String sql;
		
		sql="UPDATE bbs SET subject=?, content=? WHERE num=? AND userId=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			pstmt.setString(4, dto.getUserId());
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
	
	// 게시물 삭제
	public int deleteBoard(int num, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM bbs WHERE num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			}else {
				sql="DELETE FROM bbs WHERE num=? AND userId=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, userId);
				result = pstmt.executeUpdate();
			}
			
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
	
	// 게시물의 공감 추가
	public int insertBoardLike(int num, String userId) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="INSERT INTO bbsLike(num, userId) VALUES (?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, userId);
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
	
	// 게시물의 공감 개수
	public int countBoardLike(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM bbsLike WHERE num=?";
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

	// 게시물의 댓글 및 답글 추가
	public int insertReply(ReplyDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "INSERT INTO bbsReply(replyNum, num, userId, content, answer) VALUES (bbsReply_seq.NEXTVAL, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getAnswer());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}

	// 게시물의 댓글 개수
	public int dataCountReply(int num) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM bbsReply WHERE num=? AND answer=0";
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

	// 게시물 댓글 리스트
	public List<ReplyDTO> listReply(int num, int offset, int rows) {
		List<ReplyDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT r.replyNum, r.userId, userName, num, content, r.created, ");
			sb.append("       NVL(answerCount, 0) answerCount, ");
			sb.append("       NVL(likeCount, 0) likeCount, ");
			sb.append("       NVL(disLikeCount, 0) disLikeCount ");
			sb.append(" FROM bbsReply r ");
			sb.append("	JOIN member m ON r.userId = m.userId ");
			sb.append("	LEFT OUTER  JOIN (");
			sb.append("	     SELECT answer, COUNT(*) answerCount ");
			sb.append("      FROM bbsReply  WHERE answer != 0 ");
			sb.append("      GROUP BY answer ");
			sb.append(" ) a ON r.replyNum = a.answer ");
			sb.append(" LEFT OUTER  JOIN ( ");
			sb.append("	     SELECT replyNum,  ");
			sb.append("             COUNT(DECODE(replyLike, 1, 1)) likeCount, ");
			sb.append("             COUNT(DECODE(replyLike, 0, 1)) disLikeCount ");
			sb.append("       FROM bbsReplyLike GROUP BY replyNum  ");
			sb.append(" ) b ON r.replyNum = b.replyNum  ");
			sb.append("	WHERE num = ? AND r.answer=0 ");
			sb.append("	ORDER BY  r.replyNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto=new ReplyDTO();
				
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setAnswerCount(rs.getInt("answerCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setDisLikeCount(rs.getInt("disLikeCount"));
				
				list.add(dto);
			}
			
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
		return list;
	}

	public ReplyDTO readReply(int replyNum) {
		ReplyDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT replyNum, num, r.userId, userName, content ,r.created ");
			sb.append(" FROM bbsReply r JOIN member m ON r.userId=m.userId  ");
			sb.append(" WHERE replyNum = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, replyNum);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReplyDTO();
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
			}
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
		
		return dto;
	}
	
	// 게시물의 댓글 삭제
	public int deleteReply(int replyNum, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		if(! userId.equals("admin")) {
			ReplyDTO dto=readReply(replyNum);
			if(dto==null || (! userId.equals(dto.getUserId())))
				return result;
		}
		
		sql="DELETE FROM bbsReply ";
		sql+="  WHERE replyNum IN  ";
		sql+="  (SELECT replyNum FROM bbsReply START WITH replyNum = ?";
		sql+="    CONNECT BY PRIOR replyNum = answer)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			
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

	// 댓글의 답글 리스트
	public List<ReplyDTO> listReplyAnswer(int answer) {
		List<ReplyDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT replyNum, num, r.userId, userName, content, created, answer");
			sb.append(" FROM bbsReply r ");
			sb.append(" JOIN member m ON r.userId=m.userId");
			sb.append(" WHERE answer=?");
			sb.append(" ORDER BY replyNum DESC");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, answer);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto=new ReplyDTO();
				
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setAnswer(rs.getInt("answer"));
				
				list.add(dto);
			}
			
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
		return list;
	}
	
	// 댓글의 답글 개수
	public int dataCountReplyAnswer(int answer) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM bbsReply WHERE answer=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, answer);
			
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
	
	// 댓글의 좋아요 / 싫어요 추가
	public int insertReplyLike(ReplyDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="INSERT INTO bbsReplyLike(replyNum, userId, replyLike) VALUES (?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getReplyNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setInt(3, dto.getReplyLike());
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
	
	// 댓글의 좋아요 / 싫어요 개수
	public Map<String, Integer> countReplyLike(int replyNum) {
		Map<String, Integer> map=new HashMap<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql =  "SELECT COUNT(DECODE(replyLike, 1, 1)) likeCount,  ";
			sql += " COUNT(DECODE(replyLike, 0, 1)) disLikeCount  ";
			sql += " FROM bbsReplyLike WHERE replyNum = ? ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				map.put("likeCount", rs.getInt("likeCount"));
				map.put("disLikeCount", rs.getInt("disLikeCount"));
			}
			
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
		
		return map;
	}	
}
