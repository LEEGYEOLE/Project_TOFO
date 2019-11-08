package com.team;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			forward(req, resp, "/WEB-INF/views/team/memberList.jsp");
		}else if(uri.indexOf("userSearch.do") != -1) {
			readMember(req,resp);
		}
	}
	
	protected void readMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 팀의 멤버 보기
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
}