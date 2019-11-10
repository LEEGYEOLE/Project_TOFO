package com.main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/main/*")
public class MainServlet extends HttpServlet {
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
		HttpSession session = req.getSession();
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		if (uri.indexOf("main.do") != -1) {
			
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
		} else if (uri.indexOf("myMain.do") != -1) {
			if (session.getAttribute("member") == null) {
				session.invalidate();
				forward(req, resp, "/WEB-INF/views/main/main.jsp");
				return;
			}
			
			if (session.getAttribute("leaderId") != null) {
				session.removeAttribute("leaderId");
				session.removeAttribute("num");
				session.removeAttribute("title");
			}
			forward(req, resp, "/WEB-INF/views/main/myMain.jsp");
		}
	}
	
	protected void myMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		if (uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
		} else if (uri.indexOf("myMain.do") != -1) {
			if (session.getAttribute("member") == null) {
				forward(req, resp, "/WEB-INF/views/main/main.jsp");
				return;
			}
			forward(req, resp, "/WEB-INF/views/main/myMain.jsp");
		}
	}
}
