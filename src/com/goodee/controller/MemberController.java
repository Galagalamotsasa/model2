package com.goodee.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goodee.dto.MemberVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import util.EncryptStr;

/**
 * Servlet implementation class MemberController
 */
@WebServlet({"/MemberController", "/control.do"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("get방식! : " + request.getParameter("uid"));
		
		String uid = request.getParameter("uid");
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if(ActionFactory.getInstance().idIsDuplicate(uid)) {
				out.print("{\"resultCode\": \"true\"}");
			} else {
				out.print("{\"resultCode\": \"false\"\"}");
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ActionFactory객체를 얻어와 idIsDuplicate()를 호출
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post방식!");
		
//		String uid = request.getParameter("uid"); // 아이디
//		String pwd = request.getParameter("pwd1"); // 패스워드
		
		String path = "uploads";
		ServletContext context = getServletContext(); 
		String upfilePath = context.getRealPath(path);  // 파일이 업로드 될 실제 경로 (WAS서버 내의)
		int sizeLimit = 5 * 1024 * 1024; // 하나의 파일이 업로드 될 수 있는  maximum 사이즈
		String encodingType = "UTF-8"; // 파일 이름의 인코딩 타입
		// 파일이 실제 업로드 됨
		MultipartRequest mr = new MultipartRequest(request, upfilePath, sizeLimit, encodingType, new DefaultFileRenamePolicy());
		
		String uid = mr.getParameter("uid"); // 아이디
		String pwd = mr.getParameter("pwd1"); // 패스워드
		
		// 넘겨받은 패스워드를 SHA256 방식으로 암호화 해보자
		EncryptStr es = new EncryptStr();
		String encPwd = es.encryptionStr(pwd);
		
		System.out.println("암호화 되기 전 암호 : " + pwd);
		System.out.println("암호화 된 암호 : " + encPwd);
		
		System.out.println(mr.getFilesystemName("userImg"));
		String upFileName = "";
		if (mr.getFilesystemName("userImg") == null) { // 유저가 이미지를 올리지 않은 경우
			upFileName = "uploads/users.png"; // 기본 이미지로..
		} else { // 유저가 이미지를 올린 경우
			upFileName = "uploads/" + mr.getFilesystemName("userImg");
		}
		MemberVO member = new MemberVO(uid, encPwd, upFileName);
		System.out.println("서블릿 : " + member);
		
		response.setContentType("text/html; charset=utf-8"); // 인코딩 설정
		PrintWriter out = response.getWriter();
		
		ActionFactory af = ActionFactory.getInstance(); // ActionFactory 객체를 얻어옴
		try {
			af.registerMember(member);
			
			out.print("<script> alert('회원가입 성공!'); location.href='index.jsp';</script>");
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				out.print("<script>alert('회원가입 실패! 아이디가 중복됨'); history.back();</script>");
				e.printStackTrace();
			} else {
				out.print("<script>alert('회원가입 실패!'); history.back();</script>");
			}
		}
		
	}

}
