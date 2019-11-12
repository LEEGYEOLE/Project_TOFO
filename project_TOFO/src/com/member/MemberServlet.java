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
		// 포워딩을 위한 메소드
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
		// 로그인 폼
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 로그인 처리
		// 세션 객체. 세션 정보는 서버에 저장(로그인 정보, 권한 등)
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
				
				// 로그인 성공
				// 세션의 유지 시간을 20분으로 변경(기본은 30분)
				
				session.setMaxInactiveInterval(20 * 60);

				// 세션에 로그인 정보를 저장
			
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());

				// 세션에 member이라는 이름으로 sessionInfo 객체를 저장
				session.setAttribute("member", info);

				// 메인 화면으로 리다이렉트
				
				resp.sendRedirect(cp+"/main/myMain.do");
//				resp.sendRedirect(cp+"/main/myMain.do");
				return;
				
//				forward(req, resp, "/WEB-INF/views/member/complete.jsp");
//				return;
			}else {
				resp.sendRedirect(cp);
			}
		} catch (NullPointerException e) {
			// 로그인 실패, 아이디 불일치

		}
		sb.append("아이디 또는 패스워드가 일치하지 않습니다. <br>");
		
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		String cp = req.getContextPath();
		HttpSession session = req.getSession();

		// 세션에 저장된 정보 지우기
		// 세션의 모든 내용을
		session.removeAttribute("member");
		// 지우기 셋션을 초기화
		session.invalidate();
		// 메인화면으로 리다리렉트
		resp.sendRedirect(cp);
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입폼
		
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/member/created.jsp");

	}
	
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원가입처리
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
			String message="회원 가입이 실패했습니다.";
			
			req.setAttribute("title","회원가입");
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
		if(info==null) { // 로그인이 안된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		

		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 패스워드 확인
				HttpSession session = req.getSession();
				String cp = req.getContextPath();
				
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				if(info==null) { // 로그인이 안된 경우
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
				
					req.setAttribute("message", "<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
					forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
					return;
				} else  {
					// 회원 정보 수정 폼
					req.setAttribute("title", "회원 정보 수정");
					req.setAttribute("dto", dto);
					req.setAttribute("mode", "update");
					forward(req, resp, "/WEB-INF/views/member/created.jsp");
				}
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료
				HttpSession session = req.getSession();
				String cp = req.getContextPath();
				
				SessionInfo info = (SessionInfo)session.getAttribute("member");
				if(info==null) { // 로그인이 안된 경우
					resp.sendRedirect(cp+"/member/login.do");
					return;
				}
				
				// 수정
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
				sb.append("<b>"+dto.getUserName()+"</b>님의 정보가 수정 되었습니다. <br>");
				sb.append("메인화면으로 이동하여 다시 로그인 하시기 바랍니다.<br>");
				
				req.setAttribute("title", "회원 정보수정");
				req.setAttribute("message", sb.toString());
				
				resp.sendRedirect(cp);
				
	}
	
	
	
}
