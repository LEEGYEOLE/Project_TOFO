package com.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/*")
public class MemberServlet extends HttpServlet {

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
		// �������� ���� �޼ҵ�
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();


		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		}else if (uri.indexOf("created.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			memberSubmit(req, resp);
		}
	}
	
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ��
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ó��
		// ���� ��ü. ���� ������ ������ ����(�α��� ����, ���� ��)
		HttpSession session = req.getSession();

		MemberDAO dao = new MemberDAO();

		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		StringBuffer sb = new StringBuffer();

		try {
			MemberDTO dto = dao.readMember(id);
			if (dto.getUserPwd().equals(pwd)) {
				// �α��� ����
				// ������ ���� �ð��� 20������ ����(�⺻�� 30��)
				
				session.setMaxInactiveInterval(20 * 60);

				// ���ǿ� �α��� ������ ����
			
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());

				// ���ǿ� member�̶�� �̸����� sessionInfo ��ü�� ����
				session.setAttribute("member", info);

				// ���� ȭ������ �����̷�Ʈ
				String cp = req.getContextPath();
				resp.sendRedirect(cp+"/main/myMain.do");
//				resp.sendRedirect(cp+"/main/myMain.do");
				return;
				
//				forward(req, resp, "/WEB-INF/views/member/complete.jsp");
//				return;
			}
		} catch (NullPointerException e) {
			// �α��� ����, ���̵� ����ġ

		}
		sb.append("���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�. <br>");
		
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α׾ƿ�
		String cp = req.getContextPath();
		HttpSession session = req.getSession();

		// ���ǿ� ����� ���� �����
		// ������ ��� ������
		session.removeAttribute("member");
		// ����� �¼��� �ʱ�ȭ
		session.invalidate();
		// ����ȭ������ ���ٸ���Ʈ
		resp.sendRedirect(cp);
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ��������
		
		req.setAttribute("title", "ȸ�� ����");
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/member/created.jsp");

	}
	
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ������ó��
	}
	
	
	
}
