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
		}else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		}else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		}else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
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
		String cp = req.getContextPath();
		
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		StringBuffer sb = new StringBuffer();
		System.out.println(pwd);
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
				
				resp.sendRedirect(cp+"/main/myMain.do");
//				resp.sendRedirect(cp+"/main/myMain.do");
				return;
				
//				forward(req, resp, "/WEB-INF/views/member/complete.jsp");
//				return;
			}else {
				resp.sendRedirect(cp);
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
		MemberDTO dto=new MemberDTO();
		MemberDAO dao=new MemberDAO();
		
		dto.setUserId(req.getParameter("id"));
		dto.setUserPwd(req.getParameter("pwd"));
		dto.setUserName(req.getParameter("name"));
		dto.setBirth(req.getParameter("birth"));
		dto.setEmail(req.getParameter("email"));
		dto.setTel(req.getParameter("tel"));
		
		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message="ȸ�� ������ �����߽��ϴ�.";
			
			req.setAttribute("title","ȸ������");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		String cp = req.getContextPath();
		resp.sendRedirect(cp);
	}
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) { // �α����� �ȵ� ���
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		

		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// �н����� Ȯ��
				HttpSession session = req.getSession();
				String cp = req.getContextPath();
				
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				if(info==null) { // �α����� �ȵ� ���
					resp.sendRedirect(cp+"/member/login.do");
					return;
				}
				
				MemberDAO dao = new MemberDAO();
				MemberDTO dto = dao.readMember(info.getUserId());
				
				if(dto==null) {
					session.invalidate();
					resp.sendRedirect(cp);
					return;
				}
				
				String userPwd = req.getParameter("pwd");

				if(! dto.getUserPwd().equals(userPwd)) {
				
					req.setAttribute("message", "<span style='color:red;'>�н����尡 ��ġ���� �ʽ��ϴ�.</span>");
					forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
					return;
				} else  {
					// ȸ�� ���� ���� ��
					req.setAttribute("title", "ȸ�� ���� ����");
					req.setAttribute("dto", dto);
					req.setAttribute("mode", "update");
					forward(req, resp, "/WEB-INF/views/member/created.jsp");
				}
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ������ ���� �Ϸ�
				HttpSession session = req.getSession();
				String cp = req.getContextPath();
				
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				if(info==null) { // �α����� �ȵ� ���
					resp.sendRedirect(cp+"/member/login.do");
					return;
				}
				
				// ����
				MemberDAO dao = new MemberDAO();
				MemberDTO dto = new MemberDTO();
				
				dto.setUserId(req.getParameter("id"));
				dto.setUserName(req.getParameter("name"));
				dto.setUserPwd(req.getParameter("pwd"));				
				dto.setBirth(req.getParameter("birth"));
				dto.setEmail(req.getParameter("email"));
				dto.setTel(req.getParameter("tel"));
				
							
				try {
					dao.updateMember(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				StringBuffer sb=new StringBuffer();
				sb.append("<b>"+dto.getUserName()+"</b>���� ������ ���� �Ǿ����ϴ�. <br>");
				sb.append("����ȭ������ �̵��Ͽ� �ٽ� �α��� �Ͻñ� �ٶ��ϴ�.<br>");
				
				req.setAttribute("title", "ȸ�� ��������");
				req.setAttribute("message", sb.toString());
				
				resp.sendRedirect(cp);
				
	}
	
	
	
}
