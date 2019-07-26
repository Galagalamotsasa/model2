package com.goodee.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goodee.dto.MemberVO;
import com.goodee.dto.UserPointLog;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet({ "/MemberViewServlet", "/memberview.do" })
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		
		System.out.println(uid + "의 회원정보를 얻어와서 dispatcher 하면 됨!");
		
		try {
			
			ActionFactory af = ActionFactory.getInstance();
			MemberVO member = af.getMember(uid); // 회원 기본 정보
			
			// 회원의 포인트 내역
			List<UserPointLog> pointLogs = af.getPointLog(uid);
			
			
			request.setAttribute("member", member);
			request.setAttribute("pointLog", pointLogs);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("admin/manageMember.jsp");
			dispatcher.forward(request, response);
			
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
