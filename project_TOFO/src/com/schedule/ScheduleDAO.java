package com.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class ScheduleDAO {
	private Connection conn = DBConn.getConnection();

	// 일정 전체 가져오기
	public List<ScheduleDTO> listMonth(String startDay, String endDay, String userId, int num) {
		List<ScheduleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(
					"SELECT sche_num, num, title, TO_CHAR(sdate, 'YYYYMMDD') sdate, TO_CHAR(edate, 'YYYYMMDD') edate, stime, etime, ");
			sb.append("               color, repeat, repeat_cycle ");
			sb.append("  FROM schedule");
			sb.append("  WHERE num = ? AND ");
			sb.append("     ( ");
			sb.append("        ( ");
			sb.append("           sdate >= ? ");
			sb.append("               AND sdate <= ?  ");
			sb.append("               OR edate <= ?  ");
			sb.append("         ) OR ("); // 반복일정
			sb.append("           repeat = 1 AND repeat_cycle != 0 AND ");
			sb.append(
					"              TO_CHAR(ADD_MONTHS(sdate, 12 * repeat_cycle * TRUNC(((SUBSTR(?,1,4) - SUBSTR(sdate,1,4)) / repeat_cycle))), 'YYYYMMDD') >= ? ");
			sb.append(
					"              AND TO_CHAR(ADD_MONTHS(sdate, 12 * repeat_cycle * TRUNC(((SUBSTR(?,1,4) - SUBSTR(sdate,1,4)) / repeat_cycle))), 'YYYYMMDD') <= ? ");
			sb.append("         )");
			sb.append("    ) ");
			sb.append("  ORDER BY sdate ASC, sche_num DESC ");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, startDay);
			pstmt.setString(3, endDay);
			pstmt.setString(4, endDay);

			pstmt.setString(5, startDay);
			pstmt.setString(6, startDay);
			pstmt.setString(7, startDay);
			pstmt.setString(8, endDay);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				dto.setScheNum(rs.getInt("sche_num"));
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setsDate(rs.getString("sdate"));
				dto.seteDate(rs.getString("edate"));
				dto.setStime(rs.getString("stime"));
				dto.setEtime(rs.getString("etime"));
				dto.setColor(rs.getString("color"));
				dto.setRepeat(rs.getInt("repeat"));
				dto.setRepeat_cycle(rs.getInt("repeat_cycle"));

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

	/**
	 * 해당 유저의 이번주 일정 다 가져오기
	 * 
	 * @param date
	 * @param userId
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> listWeek(String startDay, String endDay, String userId) {
		List<Map<String, Object>> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(
					" select s.sche_num, s.num, s.title, t.title teamtitle, TO_CHAR(sdate, 'YYYYMMDD') sdate, TO_CHAR(edate, 'YYYYMMDD') edate, stime, etime, ");
			sb.append("        color, repeat, repeat_cycle,nvl(cnt,0) cnt ");
			sb.append("  FROM schedule s ");
			sb.append("  LEFT OUTER JOIN TEAM t ON s.NUM=t.num ");
			sb.append("  left outer join ( ");
			sb.append("  	select sche_num, count(sche_num) cnt from attendance where userid=? group by  sche_num ");
			sb.append("  ) a on s.sche_num=a.sche_num ");
			sb.append("  WHERE  (cnt is not null) and (sdate >= ? AND sdate <=? OR edate <=?) ");
			sb.append("  ORDER BY sdate ASC, s.sche_num DESC ");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);

			pstmt.setString(2, startDay);
			pstmt.setString(3, endDay);
			pstmt.setString(4, endDay);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sche_num", rs.getInt("sche_num"));
				map.put("num", rs.getInt("num"));
				map.put("title", rs.getString("title"));
				map.put("teamtitle", rs.getString("teamtitle"));
				map.put("sdate", rs.getString("sdate"));
				map.put("edate", rs.getString("edate"));
				map.put("color", rs.getString("color"));

				list.add(map);
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

	// 일정 등록하기.
	public int insertSchedule(ScheduleDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(" INSERT ALL ");
			sb.append(" INTO schedule( ");
			sb.append(" 	sche_num, num, userId, title, content, color, sdate, edate, ");
			sb.append(" 	stime, etime, repeat, repeat_cycle, money, addr) ");
			sb.append(" VALUES(sche_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append(" 	?, ?, ?, ?, ?,? )");
			sb.append(" INTO ATTENDANCE (USERID,SCHE_NUM) ");
			sb.append("	VALUES( ?, sche_seq.currval ) ");
			sb.append("select * from DUAL ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getColor());
			pstmt.setString(6, dto.getsDate());
			pstmt.setString(7, dto.geteDate());

			pstmt.setString(8, dto.getStime());
			pstmt.setString(9, dto.getEtime());
			pstmt.setInt(10, dto.getRepeat());
			pstmt.setInt(11, dto.getRepeat_cycle());
			pstmt.setInt(12, dto.getMoney());
			pstmt.setString(13, "empty");

			pstmt.setString(14, dto.getUserId());

			result = pstmt.executeUpdate();
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

		return result;
	}

	/**
	 * 일정 정보 가져오기
	 * 
	 * @param sche_num
	 *            일정번호
	 * @return
	 */
	public ScheduleDTO readSchedule(String userId, int sche_num) {
		ScheduleDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT s.sche_num, num, userId, title, content, nvl(cnt,0) attend,");
			sb.append("      TO_CHAR(sdate, 'YYYYMMDD') sdate, TO_CHAR(edate, 'YYYYMMDD') edate, stime, etime, ");
			sb.append("      color, repeat, repeat_cycle, created, ");
			sb.append("      money, addr, lat, lon ");
			sb.append("  FROM schedule s ");
			sb.append("  left outer join ( ");
			sb.append("  select sche_num, count(sche_num) cnt ");
			sb.append("  from attendance ");
			sb.append("  where userid=? group by  sche_num ");
			sb.append("  ) a on s.sche_num=a.sche_num");
			sb.append("  WHERE s.sche_num = ? ");
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			pstmt.setInt(2, sche_num);

			String period, s;
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new ScheduleDTO();
				dto.setScheNum(rs.getInt("sche_num"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setsDate(rs.getString("sdate"));
				s = dto.getsDate().substring(0, 4) + "-" + dto.getsDate().substring(4, 6) + "-"
						+ dto.getsDate().substring(6);
				dto.setsDate(s);
				dto.seteDate(rs.getString("edate"));
				if (dto.geteDate() != null && dto.geteDate().length() == 8) {
					s = dto.geteDate().substring(0, 4) + "-" + dto.geteDate().substring(4, 6) + "-"
							+ dto.geteDate().substring(6);
					dto.seteDate(s);
				}
				dto.setStime(rs.getString("stime"));
				if (dto.getStime() != null && dto.getStime().length() == 4) {
					s = dto.getStime().substring(0, 2) + ":" + dto.getStime().substring(2);
					dto.setStime(s);
				}
				dto.setEtime(rs.getString("etime"));
				if (dto.getEtime() != null && dto.getEtime().length() == 4) {
					s = dto.getEtime().substring(0, 2) + ":" + dto.getEtime().substring(2);
					dto.setEtime(s);
				}

				period = dto.getsDate();
				if (dto.getStime() != null && dto.getStime().length() != 0) {
					period += " " + dto.getStime();
				}
				if (dto.geteDate() != null && dto.geteDate().length() != 0) {
					period += " ~ " + dto.geteDate();
				}
				if (dto.getEtime() != null && dto.getEtime().length() != 0) {
					period += " " + dto.getEtime();
				}
				dto.setPeriod(period);

				dto.setColor(rs.getString("color"));
				dto.setRepeat(rs.getInt("repeat"));
				dto.setRepeat_cycle(rs.getInt("repeat_cycle"));
				dto.setCreated(rs.getString("created"));
				dto.setMoney(rs.getInt("money"));
				dto.setAddr(rs.getString("addr"));
				dto.setLat(rs.getString("lat"));
				dto.setLon(rs.getString("lon"));
				dto.setAttend(rs.getInt("attend"));
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

	/**
	 * 해당 그룹에서 해당 날짜의 일정 다 가져오기
	 * 
	 * @param date
	 *            날짜
	 * @param userId
	 *            유저 아이디
	 * @param num
	 *            그룹번호
	 * @return
	 */
	public List<ScheduleDTO> listDay(String date, String userId, int num) {
		List<ScheduleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(
					"SELECT sche_num, num, userId, title, TO_CHAR(sdate, 'YYYYMMDD') sdate, TO_CHAR(edate, 'YYYYMMDD') edate, ");
			sb.append("  	TO_CHAR(created, 'YYYY-MM-DD') created, color ");
			sb.append("  FROM schedule");
			sb.append("  WHERE num = ? AND ");
			sb.append("  ( ");
			sb.append("      ( ");
			sb.append("         TO_CHAR(sdate, 'YYYYMMDD') = TO_CHAR(TO_DATE(?, 'YYYYMMDD'), 'YYYYMMDD') ");
			sb.append(
					"         OR (edate IS NOT NULL AND sdate <= TO_DATE(?, 'YYYYMMDD') AND edate >= TO_DATE(?, 'YYYYMMDD')) ");
			sb.append("      ) OR ( "); // 반복일정
			sb.append(
					"           repeat=1 AND MOD(MONTHS_BETWEEN(sdate, TO_DATE(?, 'YYYYMMDD'))/12, repeat_cycle) = 0  ");
			sb.append("      ) ");
			sb.append("  ) ");
			sb.append("  ORDER BY sche_num DESC ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, date);
			pstmt.setString(3, date);
			pstmt.setString(4, date);

			pstmt.setString(5, date);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				dto.setScheNum(rs.getInt("sche_num"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setTitle(rs.getString("title"));
				dto.setsDate(rs.getString("sdate"));
				dto.seteDate(rs.getString("edate"));
				dto.seteDate(rs.getString("created"));
				dto.setColor(rs.getString("color"));

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

	// 일정 장소 수정하기
	public int updateAddress(int scheNum, String addr, String lat, String lon) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(" UPDATE Schedule ");
			sb.append(" SET addr=?, lat=?, lon=? ");
			sb.append(" WHERE sche_num=? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, addr);
			pstmt.setString(2, lat);
			pstmt.setString(3, lon);
			pstmt.setInt(4, scheNum);

			result = pstmt.executeUpdate();
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

		return result;
	}

	// 일정 참여 수정하기
	public int updateAttend(int scheNum, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(" Insert INTO attendance(sche_num,userId) ");
			sb.append(" Values(?,?) ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, scheNum);
			pstmt.setString(2, userId);

			result = pstmt.executeUpdate();
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

		return result;
	}

	// 일정 등록하기.
		public int updateSchedule(ScheduleDTO dto) {
			int result = 0;
			PreparedStatement pstmt = null;
			StringBuffer sb = new StringBuffer();

			try {
				sb.append(" UPDATE schedule set  ");
				sb.append(" 	title=?, content=?, color=?, sdate=?, edate=?, ");
				sb.append(" 	stime=?, etime=?, repeat=?, repeat_cycle=?, money=? ");
				sb.append(" WHERE sche_num=? ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getColor());
				pstmt.setString(4, dto.getsDate());
				pstmt.setString(5, dto.geteDate());

				pstmt.setString(6, dto.getStime());
				pstmt.setString(7, dto.getEtime());
				pstmt.setInt(8, dto.getRepeat());
				pstmt.setInt(9, dto.getRepeat_cycle());
				pstmt.setInt(10, dto.getMoney());
				pstmt.setInt(11, dto.getScheNum());

				result = pstmt.executeUpdate();
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

			return result;
		}
	
}
