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

/**
 * Servlet implementation class MemberManageServlet
 */
@WebServlet({ "/MemberManageServlet", "/memberManage.do" })
public class MemberManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET으로 호출됨~!");
		
		ActionFactory af = ActionFactory.getInstance();
		try {
			List<MemberVO> members = af.entireMember();
			
			request.setAttribute("members", members); // request영역에 members 객체를 속성으로 세팅
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("admin/manageMembers.jsp");
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
