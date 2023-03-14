package ezen.yorizori.web.member.controller;

import java.io.IOException;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ezen.yorizori.domain.common.factory.DaoFactory;
import ezen.yorizori.domain.member.dao.MemberDao;
import ezen.yorizori.domain.member.dto.Member;
import ezen.yorizori.domain.member.service.MemberService;
import ezen.yorizori.domain.member.service.MemberServiceImpl;
import ezen.yorizori.web.common.YZRuntimeException;

/**
 * 회원 등록 화면 요청 처리 / 회원 등록 처리 컨트롤러 서블릿
 */
@WebServlet("/member/list.do")
public class listController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//비즈니스 객체 사용
	private MemberService memberService = new MemberServiceImpl(); 
	
	/**
	 * 회원 목록 처리
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 사용자인 경우 서비스
		// 만약 비로그인이면 안내 메시지 출력
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginMember");
		
		if(member!=null) {
			List<Member> list = memberService.getMembers();
			request.setAttribute("list", list);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/member/list.jsp");
			rd.forward(request, response);			
		}else {
			throw new YZRuntimeException("로그인 후 이용할 수 있는 페이지 입니다.", "/member/list.do");
		}
	}
	
}












