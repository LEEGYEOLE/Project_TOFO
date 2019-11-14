package com.teamList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.team.TeamDAO;
import com.team.TeamDTO;

import net.sf.json.JSONObject;

@WebServlet("/teamList/*")
public class TeamListServlet extends HttpServlet {

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

		if (uri.indexOf("memberList.do") != -1) {
			readTeamMember(req, resp);
		} else if (uri.indexOf("userSearch.do") != -1) {
			readMember(req, resp);
		} else if (uri.indexOf("updateRank.do") != -1) {
			updateRank(req, resp);
		} else if (uri.indexOf("deleteTeamList.do") != -1) {
			deleteTeamList(req, resp);
		} else if (uri.indexOf("insertTeamList.do") != -1) {
			insertTeamList(req, resp);
		}
	}

	protected void readMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 모달 창에서 아이디 검색하고 싶어..
		String userId = req.getParameter("userId");
		TeamListDAO dao = new TeamListDAO();
		List<Map<String, Object>> memberList = dao.readMemberList(userId);

		req.setAttribute("memberList", memberList);

		// forward
		forward(req, resp, "/WEB-INF/views/team/searchId.jsp");
	}

	private void readTeamMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		if (session.getAttribute("num") == null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp + "/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");

		TeamListDAO dao = new TeamListDAO();
		List<HashMap<String, Object>> list = dao.listTeamMember(groupNum);

		// 모임정보 가져오기
		TeamDAO tdao = new TeamDAO();
		TeamDTO team = tdao.readTeam(groupNum);

		req.setAttribute("team", team);
		req.setAttribute("list", list);

		forward(req, resp, "/WEB-INF/views/team/memberList.jsp");
	}

	private void updateRank(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인이 안 된 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		if (session.getAttribute("num") == null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp + "/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");
		String leader = req.getParameter("leader");
		String userId = req.getParameter("userId");

		TeamListDAO dao = new TeamListDAO();

		dao.updateRank(leader, userId, groupNum);

		resp.sendRedirect(cp + "/main/myMain.do");
	}

	public void deleteTeamList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인이 안 된 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		if (session.getAttribute("num") == null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp + "/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");
		String userId = req.getParameter("userId");
		TeamListDAO dao = new TeamListDAO();
		dao.deleteTeamList(userId, groupNum);

		resp.sendRedirect(cp + "/teamList/memberList.do");
	}

	public void insertTeamList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String state = "false";

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인이 안 된 경우
			state = "loginFail";
		} else if (session.getAttribute("num") == null) {
			// 메인 화면으로 리다이렉트
			// resp.sendRedirect(cp + "/main/myMain.do");
			state = "groupNumFail";
		} else {

			int groupNum = (int) session.getAttribute("num");
			TeamListDAO dao = new TeamListDAO();
			if (dao.userCount(groupNum) > dao.dataCount(groupNum)) {
				String userId = req.getParameter("userId");
				String rank = req.getParameter("rank");
				dao.insertTeamList(userId, rank, groupNum);
				state = "true";
			} else {
				state = "over";
			}
		}

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
}