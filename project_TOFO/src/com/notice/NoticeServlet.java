package com.notice;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.util.FileManager;
import com.util.MyUtil;

@WebServlet("/notice/*")
public class NoticeServlet extends HttpServlet {

	private static final long serialVersionUID = 6652410901770551087L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	
	//포워드 만들기
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) 
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	private SessionInfo loginUser(HttpServletRequest req) throws ServletException {
		SessionInfo info = null; // 6번째
		HttpSession session = req.getSession();

		info = (SessionInfo) session.getAttribute("member");
		return info;

	}
	
	private String getFilepathname(HttpServletRequest req) throws ServletException {
		String s = null;
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		s = root + "notice" + File.separator + "notice";
		return s;

	}
	
	
	
	
	//화면이동함 
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		String uri = req.getRequestURI();
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);

		}
	}
	
	
	
	
	//글 리스트 
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAOlmpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		if(session.getAttribute("num")==null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp+"/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");
		
		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);

		}

		int rows = 10;
		String srows = req.getParameter("rows");
		if (srows != null) {
			rows = Integer.parseInt(srows);

		}

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";

		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");

		}

		int dataCount;

		if (keyword.length() == 0)
			dataCount = dao.dataCount(groupNum);
		else
			dataCount = dao.dataCount(condition, keyword, groupNum);
		
		
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		if (offset < 0)
			offset = 0;
		
		
		List<NoticeDTO> list;
		if (keyword.length() == 0)
			list = dao.listNotice(offset, rows, groupNum);
		else
			list = dao.listNotice(offset, rows, condition, keyword, groupNum);
		
		
		// 공지글
		List<NoticeDTO> listNotice = null;
		if (current_page == 1) {
			listNotice = dao.listNotice(groupNum);
			for (NoticeDTO dto : listNotice) {
				dto.setCreated(dto.getCreated().substring(0, 10));
			}

		}

		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 글 번호 만들기
		int listNum, n = 0;
		for (NoticeDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			try {
				Date date = sdf.parse(dto.getCreated());
				// gap = (curDate.getTime()-date.getTime())/(1000*60*60*24); //일자
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60); // 시간
				dto.setGap(gap);
			} catch (Exception e) {
				
			}
			dto.setCreated(dto.getCreated().split(" ")[0]);
			n++;
		}

		String query = "rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");

		}

		String listUrl = cp + "/notice/list.do?" + query;
		String articleUrl = cp + "/notice/article.do?page=" + current_page + "&" + query;

		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("listNotice", listNotice);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("groupNum", groupNum);

		forward(req, resp, "/WEB-INF/views/notice/noticeList.jsp");

	}
	
	
	//글 등록 폼 
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String leaderId = (String)req.getSession().getAttribute("leaderId");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;

		}

		if (!info.getUserId().equals(leaderId)) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;

		}

		HttpSession session = req.getSession();
		if(session.getAttribute("num")==null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp+"/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");
		
		req.setAttribute("mode", "created");
		req.setAttribute("groupNum", groupNum);
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");

	}
	
	//글 등록
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String leaderId = (String)req.getSession().getAttribute("leaderId");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;

		}

		if (!info.getUserId().equals(leaderId)) {
			resp.sendRedirect(cp + "notice/list.do");
			return;

		}
		
		HttpSession session = req.getSession();
		if(session.getAttribute("num")==null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp+"/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");

		String pathname = getFilepathname(req);
		File f = new File(pathname);
		if (!f.exists()) {
			f.mkdirs();

		}

		// 파라미터 넘겨 받아 디비에 저장
		String encType = "utf-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = null;
		mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		NoticeDAO dao = new NoticeDAOlmpl();
		NoticeDTO dto = new NoticeDTO();

		dto.setUserId(info.getUserId());
		dto.setGroupNum(groupNum);
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		
		
//		String[] notice= 
//		String notice = "0";
//		if (mreq.getParameterValues("notice") != null)
//			notice = mreq.getParameterValues("notice")[0];
//		dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));

		
		if(mreq.getParameter("notice")!=null) {
	         dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));
	      }
		
//		dto.setNotice(Integer.parseInt());
		// 좀있다가 하자~~~+

		if (mreq.getFile("upload") != null) {
			dto.setSaveFilename(mreq.getFilesystemName("upload"));
			dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			dto.setFilesize(mreq.getFile("upload").length());

		}

		dao.insertNotice(dto);

		resp.sendRedirect(cp + "/notice/list.do"); // 여기다가 넣어야지 다시 간다.

	}
	
	
	//글보기
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		if(session.getAttribute("num")==null) {
			// 메인 화면으로 리다이렉트
			resp.sendRedirect(cp+"/main/myMain.do");
			return;
		}
		int groupNum = (int) session.getAttribute("num");

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";

		}

		keyword = URLDecoder.decode(keyword, "UTF-8");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");

		}

		// DB에서 가져오는 작업
		NoticeDAO dao = new NoticeDAOlmpl();
		dao.updateHitCount(num);

		NoticeDTO dto = dao.readNotice(num);
		if (dto == null) {
			resp.sendRedirect(cp + "/notice/list.do?" + query);
			return;
		}

		NoticeDTO preDto = dao.preReadNotice(num, condition, keyword, groupNum);
		NoticeDTO nextDto = dao.nextReadNotice(num, condition, keyword, groupNum);

		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preDto);
		req.setAttribute("nextReadDto", nextDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/notice/article.jsp");

	}

	
	
	//글 수정 폼 
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;

		}

		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));

		NoticeDAO dao = new NoticeDAOlmpl();
		NoticeDTO dto = dao.readNotice(num);
		if (dto == null || !info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page);
			return;

		}

		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/notice/created.jsp");

	}
	
	//글 수정
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;

		}
		
		
		NoticeDAO dao = new NoticeDAOlmpl();
		NoticeDTO dto = new NoticeDTO();
		
		String pathname = getFilepathname(req);
		File f = new File(pathname);
		if (!f.exists()) {
			f.mkdirs();
		
		}
		
		String encType = "utf-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = null;
		mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());


		dto.setUserId(info.getUserId());
		
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));
		
		if(mreq.getParameter("notice")!=null) {
	         dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));
	      }
		
		
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		
		dto.setSaveFilename(mreq.getParameter("saveFilename"));
		dto.setOriginalFilename(mreq.getParameter("originalFilename"));
		dto.setFilesize(Integer.parseInt(mreq.getParameter("filesize")));
	
		
		if (mreq.getFile("upload") != null) {
			if(dto.getSaveFilename().length()!=0) {
				FileManager.doFiledelete(pathname, dto.getSaveFilename());
			}
			dto.setSaveFilename(mreq.getFilesystemName("upload"));
			dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			dto.setFilesize(mreq.getFile("upload").length());

		}
		
		String page = mreq.getParameter("page");
		dao.updateNotice(dto);
		resp.sendRedirect(cp+"/notice/list.do?page="+page);

		
	}
	
	//글 삭제 
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;

		}
		
		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("num"));
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
			
		}
		
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page+"&num="+num;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
		           +URLEncoder.encode(keyword, "UTF-8");
		}
		
		NoticeDAO dao=new NoticeDAOlmpl();
		dao.deleteNotice(num, info.getUserId());
		
		resp.sendRedirect(cp+"/notice/list.do?"+query);
	
	}

}
