package com.review;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
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

@WebServlet("/review/*")
public class ReviewServlet extends HttpServlet {
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
		// �������� ���� �޼ҵ�
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		// �α��� �ȵ��� ���
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // �α��ε��� ���� ���
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		// �Խù�����..
		String root = session.getServletContext().getRealPath("/"); // ������ �Խñ��� ������ ���
		pathname = root + "uploads" + File.separator + "picture";

		File f = new File(pathname);
		if (!f.exists()) { // ������ �������� ������
			f.mkdirs();
		}
		

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("review.do") != -1) {
			reviewdetail(req, resp);
		} else if (uri.indexOf("created.do") != -1) {
			createdForm(req, resp);
		}else if (uri.indexOf("created_ok.do") != -1) {
			createdSubmit(req, resp);
		}else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		int rows = 5;

		String condition = req.getParameter("condition");

		if (condition == null) {
			condition = "att";
		}

		// int num = Integer.parseInt(req.getParameter("num"));//�׷��ȣ�޾ƿ���
		int num = 4;
		String userid = info.getUserId();// ���� �α��� ���� ���̵�

		// ������ī��Ʈ ���� ����
		int dataCount = dao.dataCount(num, userid, condition);

		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		if (offset < 0)
			offset = 0;

		List<ReviewDTO> list;

		if (condition.equalsIgnoreCase("att")) {
			list = dao.reviewlist(num, offset, rows, userid);
		} else {
			list = dao.reviewlist(num, offset, rows, condition, userid);
		}

		int listNum, n = 0;

		for (ReviewDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			n++;
		}

		String query = "rows=" + rows + "&condition=" + condition;

		String listUrl = cp + "/review/list.do?" + query;
		String articleUrl = cp + "/review/review.do?page=" + current_page + "&" + query;

		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("articleUrl", articleUrl);

		forward(req, resp, "/WEB-INF/views/review/reviewList.jsp");
	}

	// ��ü ���� �ı⿡�� �ϳ��� ���� �ı� �Խ������� ���� !!
	protected void reviewdetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}


		ReviewDAO dao = new ReviewDAO();

		int schedule_num = Integer.parseInt(req.getParameter("num"));
		int groupNum = Integer.parseInt(req.getParameter("groupnum"));
		String condition = req.getParameter("condition");
		String page = req.getParameter("page");


		int rows = 10;


		
		// �������� �� ������ �޾ƿ����� ���(���� ���� ǥ�õǴ� �κ�)
		ReviewDTO dto = dao.review(schedule_num);

		// �׷�ȿ� �ϳ��� ������ ���ؼ� �ı���� �����´�.
		List<ReviewDTO> listdto = dao.guestbooklist(schedule_num, groupNum);

		
		// ���䵵 �����;߰���?
		List<ReviewDTO> photolist = null;
		photolist = dao.photolist(schedule_num);

		// �����ο��� �����;��մϴ�
		List<ReviewDTO> personlist = null;
		personlist = dao.personlist(schedule_num);
		
		

	
		
		req.setAttribute("page", page);		
		req.setAttribute("rows", rows);
		req.setAttribute("dto", dto);
		req.setAttribute("listdto", listdto);
		req.setAttribute("schedule_num", schedule_num);
		req.setAttribute("groupNum", groupNum);
		req.setAttribute("photolist", photolist);
		req.setAttribute("personlist", personlist);
		req.setAttribute("condition", condition);
		
		forward(req, resp, "/WEB-INF/views/review/review.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/review/review.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String encType = "utf-8";
		int maxSize = 5 * 1024 * 1024;

		MultipartRequest mreq = null;

		mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		// �Ķ���� �Ѱ� �ޱ�
		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao = new ReviewDAO();

		dto.setUserId(info.getUserId());
		dto.setContentDetail(mreq.getParameter("reviewcontetn"));
		dto.setReviewNum(Integer.parseInt(mreq.getParameter("reviewNum")));

		// �ı� ���
		dao.insertreview(dto);

		Enumeration<?> e = mreq.getFileNames();
		while (e.hasMoreElements()) {

			String paramName = (String) e.nextElement();

			if (mreq.getFile(paramName) == null)
				continue;

			String saveFilename = mreq.getFilesystemName(paramName);
			// String originalFilename = mreq.getOriginalFileName(paramName);

			dto.setImageFilename(saveFilename);

			// ���� ���
			dao.insertphoto(dto);
		}

		int page = Integer.parseInt(mreq.getParameter("page"));
		int rows = Integer.parseInt(mreq.getParameter("rows"));
		int num = Integer.parseInt(mreq.getParameter("reviewNum"));
		int groupnum = Integer.parseInt(mreq.getParameter("groupnum"));
		String condition = mreq.getParameter("condition");

		String query = "page=" + page + "&rows=" + rows + "&condition=" + condition + "&num=" + num + "&groupnum="
				+ groupnum;
		resp.sendRedirect(cp + "/review/review.do?" + query);

	}



	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}


		
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		
		int reviewnum=Integer.parseInt(req.getParameter("reviewnum"));
		int groupNum=Integer.parseInt(req.getParameter("groupnum"));
		int scheNum=Integer.parseInt(req.getParameter("num"));
		int page=Integer.parseInt(req.getParameter("page"));
		int rows = Integer.parseInt(req.getParameter("rows"));
		String condition =req.getParameter("condition");
		
		
		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao =new ReviewDAO();
		
		dto = dao.readMember(reviewnum);
		
		
			if(dto==null) {
		  resp.sendRedirect(cp+ "/member/login.do");
		  return; }
		

		  // �Խù��� �ø� ����ڳ� admin�� �ƴϸ� 
		if(! dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin"))
		{		
			
			forward(req, resp, "/WEB-INF/views/review/check.jsp");
			//resp.sendRedirect(cp+"/review/review.do?page="+page+"&rows="+rows+"&condition="+condition+"&num="+scheNum+"&groupnum="+groupNum); 
			return; 
		}
		  
				  

		
		//���� �̹��� ���� �����ϰ� �ڽ��� picture���̺� ����~
		List<ReviewDTO> list = dao.readphotofile(reviewnum);
		
		for(ReviewDTO dto2 : list)
		{
			FileManager.doFiledelete(pathname, dto2.getImageFilename());	
		}
		
		dao.deletePhoto(reviewnum);

		// ���̺� ������ ���� 
		dao.deletereview(reviewnum);
		
		resp.sendRedirect(cp+"/review/review.do?page="+page+"&rows="+rows+"&condition="+condition+"&num="+scheNum+"&groupnum="+groupNum); 
		
		
	}
}
