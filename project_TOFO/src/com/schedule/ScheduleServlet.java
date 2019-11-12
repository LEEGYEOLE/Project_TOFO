package com.schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

import net.sf.json.JSONObject;

@WebServlet("/schedule/*")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		// AJAX에서 로그인이 안된 경우 403이라는 에러 코드를 던진다.
//				String header=req.getHeader("AJAX");
//				if(header!=null && header.equals("true")  && info==null) {
//					resp.sendError(403);
//					return;
//				}

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		if (uri.indexOf("list.do") != -1) {
			monthSchedule(req, resp);
		} else if (uri.indexOf("insert.do") != -1) {
			insertSubmit(req, resp);
		} else if (uri.indexOf("day.do") != -1) {
			daySchedule(req, resp);
		} else if (uri.indexOf("updateAddr.do") != -1) {
			updateAddr(req, resp);
		} else if (uri.indexOf("updateAddr_ok.do") != -1) {
			updateAddrSubmit(req, resp);
		} else if (uri.indexOf("attend.do") != -1) {
			attend(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	private void monthSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		if (info == null) {
			resp.sendRedirect(cp);
			return;
		}
		String leaderId = "";
		if (req.getParameter("leaderId") != null)
			leaderId = req.getParameter("leaderId");
		else if (session.getAttribute("leaderId") != null)
			leaderId = (String) session.getAttribute("leaderId");
		int num = 0;
		if (req.getParameter("num") != null)
			num = Integer.parseInt(req.getParameter("num"));
		else if (session.getAttribute("num") != null)
			num = (int) session.getAttribute("num");

		String title = "";
		if (req.getParameter("title") != null)
			title = req.getParameter("title");

		/* 그룹에 처음 접속할 때 세션에 그룹장, 그룹번호, 그룹제목을 저장한다. */
		if (session.getAttribute("leaderId") == null) {
			session.setAttribute("leaderId", leaderId);
		} else {
		}
		if (session.getAttribute("num") == null) {
			session.setAttribute("num", num);
		}
		if (session.getAttribute("title") == null) {
			session.setAttribute("title", title);
		}

		ScheduleDAO dao = new ScheduleDAO();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int todayYear = year;
		int todayMonth = month;
		int todayDate = cal.get(Calendar.DATE);

		String y = req.getParameter("year");
		String m = req.getParameter("month");

		if (y != null)
			year = Integer.parseInt(y);
		if (m != null)
			month = Integer.parseInt(m);

		// year년 month월 1일의 요일
		cal.set(year, month - 1, 1);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7

		// 첫주의 year년도 month월 1일 이전 날짜
		Calendar scal = (Calendar) cal.clone();
		scal.add(Calendar.DATE, -(week - 1));
		int syear = scal.get(Calendar.YEAR);
		int smonth = scal.get(Calendar.MONTH) + 1;
		int sdate = scal.get(Calendar.DATE);

		// 마지막주의 year년도 month월 말일주의 토요일 날짜
		Calendar ecal = (Calendar) cal.clone();
		// year년도 month월 말일
		ecal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		// year년도 month월 말일주의 토요일
		ecal.add(Calendar.DATE, 7 - ecal.get(Calendar.DAY_OF_WEEK));
		int eyear = ecal.get(Calendar.YEAR);
		int emonth = ecal.get(Calendar.MONTH) + 1;
		int edate = ecal.get(Calendar.DATE);

		// 스케쥴 가져오기
		String startDay = String.format("%04d%02d%02d", syear, smonth, sdate);
		String endDay = String.format("%04d%02d%02d", eyear, emonth, edate);
		List<ScheduleDTO> list = dao.listMonth(startDay, endDay, info.getUserId(), num);

		String s;
		String[][] days = new String[cal.getActualMaximum(Calendar.WEEK_OF_MONTH)][7];

		// 1일 앞의 전달 날짜 및 일정 출력
		// startDay ~ endDay 까지 처리
		int cnt;
		for (int i = 1; i < week; i++) {
			s = String.format("%04d%02d%02d", syear, smonth, sdate);
			days[0][i - 1] = "<span class='textDate preMonthDate' data-date='" + s + "' >" + sdate + "</span>";

			cnt = 0;
			for (ScheduleDTO dto : list) {
				int sd8 = Integer.parseInt(dto.getsDate());
				int sd4 = Integer.parseInt(dto.getsDate().substring(4));
				int ed8 = -1;
				if (dto.geteDate() != null) {
					ed8 = Integer.parseInt(dto.geteDate());
				}
				int cn8 = Integer.parseInt(s);
				int cn4 = Integer.parseInt(s.substring(4));

				if (cnt == 4) {
					days[0][i - 1] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
					break;
				}

				if ((dto.getRepeat() == 0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8))
						|| (dto.getRepeat() == 1 && sd4 == cn4)) {
					days[0][i - 1] += "<span class='scheduleSubject' data-date='" + s + "' data-num='"
							+ dto.getScheNum() + "' style='background:" + dto.getColor() + ";' >" + dto.getTitle()
							+ "</span>";
					cnt++;
				} else if ((dto.getRepeat() == 0 && (sd8 > cn8 && ed8 < cn8)) || (dto.getRepeat() == 1 && sd4 > cn4)) {
					break;
				}
			}
			sdate++;
		}

		// year년도 month월 날짜 및 일정 출력
		int row, n = 0;

		jump: for (row = 0; row < days.length; row++) {
			for (int i = week - 1; i < 7; i++) {
				n++;
				s = String.format("%04d%02d%02d", year, month, n);

				if (i == 0) {
					days[row][i] = "<span class='textDate sundayDate' data-date='" + s + "' >" + n + "</span>";
				} else if (i == 6) {
					days[row][i] = "<span class='textDate saturdayDate' data-date='" + s + "' >" + n + "</span>";
				} else {
					days[row][i] = "<span class='textDate nowDate' data-date='" + s + "' >" + n + "</span>";
				}

				cnt = 0;
				for (ScheduleDTO dto : list) {
					int sd8 = Integer.parseInt(dto.getsDate());
					int sd4 = Integer.parseInt(dto.getsDate().substring(4));
					int ed8 = -1;
					if (dto.geteDate() != null) {
						ed8 = Integer.parseInt(dto.geteDate());
					}
					int cn8 = Integer.parseInt(s);
					int cn4 = Integer.parseInt(s.substring(4));

					if (cnt == 3) {
						days[row][i] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
						break;
					}

					if ((dto.getRepeat() == 0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8))
							|| (dto.getRepeat() == 1 && sd4 == cn4)) {
						days[row][i] += "<span class='scheduleSubject' data-date='" + s + "' data-num='"
								+ dto.getScheNum() + "' style='background:" + dto.getColor() + ";' >" + dto.getTitle()
								+ "</span>";
						cnt++;
					} else if ((dto.getRepeat() == 0 && (sd8 > cn8 && ed8 < cn8))
							|| (dto.getRepeat() == 1 && sd4 > cn4)) {
						break;
					}
				}

				if (n == cal.getActualMaximum(Calendar.DATE)) {
					week = i + 1;
					break jump;
				}
			}
			week = 1;
		}

		// year년도 month월 마지막 날짜 이후 일정 출력
		if (week != 7) {
			n = 0;
			for (int i = week; i < 7; i++) {
				n++;
				s = String.format("%04d%02d%02d", eyear, emonth, n);
				days[row][i] = "<span class='textDate nextMonthDate' data-date='" + s + "' >" + n + "</span>";

				cnt = 0;
				for (ScheduleDTO dto : list) {
					int sd8 = Integer.parseInt(dto.getsDate());
					int sd4 = Integer.parseInt(dto.getsDate().substring(4));
					int ed8 = -1;
					if (dto.geteDate() != null) {
						ed8 = Integer.parseInt(dto.geteDate());
					}
					int cn8 = Integer.parseInt(s);
					int cn4 = Integer.parseInt(s.substring(4));

					if (cnt == 4) {
						days[row][i] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
						break;
					}

					if ((dto.getRepeat() == 0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8))
							|| (dto.getRepeat() == 1 && sd4 == cn4)) {
						days[row][i] += "<span class='scheduleSubject' data-date='" + s + "' data-num='"
								+ dto.getScheNum() + "' style='background:" + dto.getColor() + ";' >" + dto.getTitle()
								+ "</span>";
						cnt++;
					} else if ((dto.getRepeat() == 0 && (sd8 > cn8 && ed8 < cn8))
							|| (dto.getRepeat() == 1 && sd4 > cn4)) {
						break;
					}
				}

			}
		}

		String today = String.format("%04d%02d%02d", todayYear, todayMonth, todayDate);

		req.setAttribute("list", list);
		req.setAttribute("num", num);
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("todayYear", todayYear);
		req.setAttribute("todayMonth", todayMonth);
		req.setAttribute("todayDate", todayDate);
		req.setAttribute("today", today);
		req.setAttribute("days", days);

		forward(req, resp, "/WEB-INF/views/schedule/scheduleList.jsp");
	}
	
	private void insertSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String state = "true";

		ScheduleDAO dao = new ScheduleDAO();
		ScheduleDTO dto = new ScheduleDTO();

		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setUserId(info.getUserId());
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setColor(req.getParameter("color"));
		dto.setsDate(req.getParameter("sday").replaceAll("-", ""));
		dto.seteDate(req.getParameter("eday").replaceAll("-", ""));
		dto.setStime(req.getParameter("stime").replaceAll(":", ""));
		dto.setEtime(req.getParameter("etime").replaceAll(":", ""));
		if (req.getParameter("allDay") != null) {
			dto.setStime("");
			dto.setEtime("");
		}

		if (dto.getStime().length() == 0 && dto.getEtime().length() == 0 && dto.getsDate().equals(dto.geteDate()))
			dto.seteDate("");

		dto.setRepeat(Integer.parseInt(req.getParameter("repeat")));
		if (req.getParameter("repeat_cycle").length() != 0) {
			dto.setRepeat_cycle(Integer.parseInt(req.getParameter("repeat_cycle")));

			dto.seteDate("");
			dto.setStime("");
			dto.setEtime("");
		}
		dto.setMoney(Integer.parseInt(req.getParameter("money")));

		int result = dao.insertSchedule(dto);
		if (result < 1)
			state = "false";

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	private void daySchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		ScheduleDAO dao = new ScheduleDAO();

		String date = req.getParameter("date");
		String num = req.getParameter("num");
		String scheNum = req.getParameter("scheNum");

		Calendar cal = Calendar.getInstance();

		// 오늘날짜
		String today = String.format("%04d%02d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DATE));
		if (date == null || !Pattern.matches("^\\d{8}$", date)) {
			date = today;
		}
		// 일일 일정을 출력할 년, 월, 일
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6));

		cal.set(year, month - 1, day);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DATE);

		cal.set(year, month - 1, 1);
		int week = cal.get(Calendar.DAY_OF_WEEK);

		// 테이블에서 일일 전체일절 리스트 가져오기 가져오기
		date = String.format("%04d%02d%02d", year, month, day);
		List<ScheduleDTO> list = dao.listDay(date, info.getUserId(), Integer.parseInt(num));

		int sch_num = 0;
		ScheduleDTO dto = null;

		// 파라미터로 받은 일정번호가 있으면 해당 번호에 해당하는 일정정보 가져오기
		if (scheNum != null) {
			sch_num = Integer.parseInt(scheNum);
			dto = dao.readSchedule(info.getUserId(), sch_num);
		}
		// 디비에 일정번호에 해당하는 정보가 없으면서 (dto==null, 중간에 변경 된 경우), 해당 날짜의 일정이 여러개 존재할 경우
		// 여러개중 첫번째 일정에 대한 정보를 가져와 dto에 저장한다.
		if (dto == null && list.size() > 0) {
			dto = dao.readSchedule(info.getUserId(), list.get(0).getNum());
		}
		req.setAttribute("num", num);
		req.setAttribute("scheNum", scheNum);
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("day", day);
		req.setAttribute("date", date);
		req.setAttribute("dto", dto);
		req.setAttribute("list", list);

		forward(req, resp, "/WEB-INF/views/schedule/article.jsp");
	}

	protected void updateAddr(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 위치 수정

		String cp = req.getContextPath();
		String scheNum = req.getParameter("scheNum");
		String date = req.getParameter("date");
		String num = req.getParameter("num");
		String title = req.getParameter("title");
		req.setAttribute("scheNum", scheNum);
		req.setAttribute("date", date);
		req.setAttribute("num", num);
		req.setAttribute("title", title);
		forward(req, resp, "/WEB-INF/views/schedule/addrUpdate.jsp");
	}

	protected void updateAddrSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 일정 장소 수정 완료
		ScheduleDAO dao = new ScheduleDAO();
		String cp = req.getContextPath();
		String date = req.getParameter("date");
		String num = req.getParameter("num");
		String snum = req.getParameter("scheNum");
		String address = req.getParameter("address");
		String lat = req.getParameter("lat");
		String lon = req.getParameter("lon");

		int scheNum = Integer.parseInt(snum);
		int result = dao.updateAddress(scheNum, address, lat, lon);

		resp.sendRedirect(cp + "/schedule/day.do?date=" + date + "&scheNum=" + scheNum + "&num=" + num);
	}

	protected void attend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 참여하게해줄게
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {

		}
		ScheduleDAO dao = new ScheduleDAO();
		String cp = req.getContextPath();
		String date = req.getParameter("date");
		String num = req.getParameter("num");
		String snum = req.getParameter("scheNum");
		int scheNum = Integer.parseInt(snum);

		int result = dao.updateAttend(scheNum, info.getUserId());

		resp.sendRedirect(cp + "/schedule/day.do?date=" + date + "&scheNum=" + scheNum + "&num=" + num);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 일정 삭제
	}
}
