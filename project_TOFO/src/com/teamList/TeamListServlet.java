package com.teamList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

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
		} else if(uri.indexOf("userSearch.do") != -1) {
			readMember(req,resp);
		} else if(uri.indexOf("updateRank.do")!=-1) {
			updateRank(req, resp);
		} else if(uri.indexOf("deleteTeamList.do")!=-1) {
			deleteTeamList(req, resp);
		}
	}
	
	protected void readMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 모달 창에서 아이디 검색하고 싶어..
		String userId = req.getParameter("userId");
		TeamListDAO dao = new TeamListDAO();
		List<HashMap<String, Object>> memberList = dao.readMemberList(userId);
		
		req.setAttribute("memberList", memberList);
	}
	
	private void readTeamMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String snum = req.getParameter("num");
		int num;
		if(snum==null) {
			num = 7;
		}else {
			num =Integer.parseInt(req.getParameter("num"));
		}
		TeamListDAO dao = new TeamListDAO();
		List<HashMap<String, Object>> list = dao.listTeamMember(num);
		
		req.setAttribute("list", list);
		req.setAttribute("num", num);

		forward(req, resp, "/WEB-INF/views/team/memberList.jsp");
	}
	
	private void updateRank(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인이 안 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String snum = req.getParameter("num");
		int num = Integer.parseInt(snum);
		String leader = req.getParameter("leader");
		String userId = req.getParameter("userId");
		
		TeamListDAO dao = new TeamListDAO();
		
		dao.updateRank(leader, userId, num);
		
		resp.sendRedirect(cp+"/schedule/list.do?num="+num);
	}
	
	public void deleteTeamList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인이 안 된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		String snum = req.getParameter("num");
		int num = Integer.parseInt(snum);
		String userId = req.getParameter("userId");
		TeamListDAO dao = new TeamListDAO();
		dao.deleteTeamList(userId, num);
		
		resp.sendRedirect(cp+"/teamList/memberList.do?num="+num);
	}
}