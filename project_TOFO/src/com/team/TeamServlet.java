package com.team;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.schedule.ScheduleDAO;
import com.schedule.ScheduleDTO;
import com.util.FileManager;

@WebServlet("/team/*")
public class TeamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String pathname;

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
		if (info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + File.separator + "uploads" + File.separator + "photo" + File.separator + "team";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}

		if (uri.indexOf("list.do") != -1) {
			teamList(req, resp);
		} else if (uri.indexOf("insert.do") != -1) {
			insertSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			 updateSubmit(req, resp);
		} else if (uri.indexOf("deleteTeamList.do") != -1) {
			// deleteTeamList(req, resp);
		}
	}

	/**
	 * 모임 목록 불러오기
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void teamList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		TeamDAO tdao = new TeamDAO();
		ScheduleDAO scheDao = new ScheduleDAO();

		/* 오늘 날짜, 이번주 시작 날짜, 이번주 끝 날짜 구하기 */
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int date = cal.get(Calendar.DATE);
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7

		Calendar scal = (Calendar) cal.clone();
		scal.add(Calendar.DATE, -((week + 7) % 9));
		int syear = scal.get(Calendar.YEAR);
		int smonth = scal.get(Calendar.MONTH) + 1;
		int sdate = scal.get(Calendar.DATE);
		int sweek = scal.get(Calendar.DAY_OF_WEEK); // 1~7

		Calendar ecal = (Calendar) cal.clone();
		ecal.add(Calendar.DATE, 7 - ecal.get(Calendar.DAY_OF_WEEK) + 1);
		int eyear = ecal.get(Calendar.YEAR);
		int emonth = ecal.get(Calendar.MONTH) + 1;
		int edate = ecal.get(Calendar.DATE);
		int eweek = ecal.get(Calendar.DAY_OF_WEEK); // 1~7

		String[] ww = {"일","월","화","수","목","금","토"};
		String startDay = String.format("%04d%02d%02d", syear, smonth, sdate);
		String endDay = String.format("%04d%02d%02d", eyear, emonth, edate);
		String toDay = String.format("%04d%02d%02d", year, month, date);
		// 이번주 내 일정 가져오기
		System.out.println(info.getUserId());
		List<Map<String, Object>> scheList = scheDao.listWeek(startDay, endDay, info.getUserId());

		String[] weeks = new String[7];
		Calendar scal1 = (Calendar) scal.clone();
		String s,s1;
		for (int i = 0; i < weeks.length; i++) {
			weeks[i]="";
			s = String.format("%04d%02d%02d", scal1.get(Calendar.YEAR), scal1.get(Calendar.MONTH)+1, scal1.get(Calendar.DATE));
			s1 = String.format("%04d%02d%02d", scal1.get(Calendar.YEAR), scal1.get(Calendar.MONTH)+1, scal1.get(Calendar.DATE));
			
			for (Map<String, Object> map : scheList) {
				String sd = (String) (map.get("sdate"));
				String ed = (String) (map.get("sdate"));
				if (map.get("edate") != null) {
					ed=(String) map.get("edate");
				}
				if (sd.compareTo(s)<=0&&ed.compareTo(s)>=0) {
					if(s1.equals(toDay))
						weeks[i] += "<tr style='background: #fff3d0;'><td>"+scal1.get(Calendar.DATE)+"("+ww[(i+1)%7]+")"+"</td><td>"+(String)(map.get("teamtitle"))+"</td><td style='color:"+(String)(map.get("color"))+";'>"+(String)(map.get("title"))+"</td></tr>";
					else weeks[i] += "<tr><td>"+scal1.get(Calendar.DATE)+"("+ww[(i+1)%7]+")"+"</td><td>"+(String)(map.get("teamtitle"))+"</td><td style='color:"+(String)(map.get("color"))+";'>"+(String)(map.get("title"))+"</td></tr>";
						
				}
			}
			scal1.add(Calendar.DATE, 1);
		}

		String priod = String.format("%04d/%02d / %02d ~ %04d/%02d/%02d", syear, smonth, sdate, eyear, emonth, edate);
		
		// 모임목록 가져오기
		List<TeamDTO> list = tdao.listTeam(info.getUserId());

		req.setAttribute("priod", priod);
		req.setAttribute("list", list);
		req.setAttribute("weeks", weeks);

		forward(req, resp, "/WEB-INF/views/main/myMain.jsp");
	}

	protected void readTeam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		String cp = req.getContextPath();
		TeamDAO dao = new TeamDAO();
		TeamDTO dto = dao.readTeam(Integer.parseInt((String)session.getAttribute("num")));
	}
	
	
	/**
	 * 모임 추가하기
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void insertSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		String cp = req.getContextPath();
		String encType = "UTF-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		TeamDAO dao = new TeamDAO();
		TeamDTO dto = new TeamDTO();
		// 이미지 파일을 업로드 한경우

		dto.setTitle(mreq.getParameter("title"));
		dto.setContent(mreq.getParameter("content"));
		dto.setUserId(info.getUserId());
		dto.setUserCount(Integer.parseInt(mreq.getParameter("userCount")));
		if (mreq.getFile("imageFilename") == null) {
			dto.setImageFilename("");
		} else {
			// 서버에 저장된 파일명
			String saveFilename = mreq.getFilesystemName("imageFilename");

			// 파일이름변경
			saveFilename = FileManager.doFilerename(pathname, saveFilename);

			dto.setImageFilename(saveFilename);
		}

		// 저장
		dao.insertTeam(dto);
		resp.sendRedirect(cp + "/main/myMain.do");

	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		String cp = req.getContextPath();

		TeamDAO dao = new TeamDAO();
		TeamDTO dto = new TeamDTO();
		int groupNum = (int) session.getAttribute("num");

		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setUserCount(Integer.parseInt(req.getParameter("userCount")));
		dto.setNum(groupNum);

		// 수정
		dao.updateTeam(dto);
		resp.sendRedirect(cp + "/main/myMain.do");

	}
	
	protected void weekList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		if (info == null) {
			resp.sendRedirect(cp);
			return;
		}

		// 날짜 계산
	}

}