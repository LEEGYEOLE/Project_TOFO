package com.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

@WebServlet("/team/*")
public class TeamServlet extends HttpServlet {

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
		} else if(uri.indexOf("")!=-1) {
			
		}
	}
	
	protected void readMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 모달 창에서 아이디 검색하고 싶어..
		String userId=req.getParameter("userId");
		TeamDAO dao=new TeamDAO();
		TeamDTO dto=dao.readMember(userId);
		String aa;
		if(dto==null) {
		aa="<p>해당하는 유저가 존재하지 않습니다.</p>";
		}else {
		aa="<p>"+dto.getUserId()+"</p>";
		}
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		out.print(aa);
	}
	
	private void readTeamMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TeamDAO dao = new TeamDAO();
		List<TeamDTO> list;
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		// int num =Integer.parseInt(req.getParameter("num"));
		
		try {
			list = dao.readTeamMember(7); // 리스트로 받았어
			req.setAttribute("list", list); // 키, 값
		} catch (Exception e) {
			String cp = req.getContextPath();
			resp.sendRedirect(cp);
		}
		forward(req, resp, "/WEB-INF/views/team/memberList.jsp");
	}
}