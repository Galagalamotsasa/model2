package com.goodee.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.goodee.dto.MemberVO;
import com.goodee.dto.UserPointLog;

import util.DBManagement;

public class MemberDAO {
	//-------------------------------- MemberDAO 싱글톤 --------------------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() { }
	
	public static MemberDAO getInstance() {
		if (instance == null) {
			return new MemberDAO();
		}
		
		return instance;
	}
	//------------------------------------------------------------------------------------------------
	//================================================================================================
	// 로그인 처리 -----------------------------------------------------------------------------------
	public MemberVO loginProcess(String uid, String pwd) throws NamingException, SQLException {
		// login에 성공하면 login한 유저의 정보(MemberVO) 반환, login에 실패 하면 null 반환
		Connection con = DBManagement.getConnection();
		String query = "select * from membervo where userid = ? and sha256pwd = ?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, uid);
		pstmt.setString(2, pwd);
		
		MemberVO loginMember = null;
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) { // row가 있을 동안
			
			// 최근 로그인 일자가 1일을 넘겼다면~ addLoginPoint(uid) 호출
			Date lastlogindate = rs.getDate("lastlogindate"); // 최근 로그인 날짜 얻어오기
			if (lastlogindate != null) {
				long lastloginTimeStamp = lastlogindate.getTime(); // 최근 로그인 날짜를 TimeStamp타입으로 변경
				long currentTimeStamp = System.currentTimeMillis(); // 현재 시간을 TimeStamp 값으로 얻어옴
				
				long difference = (currentTimeStamp - lastloginTimeStamp); //  / (1000 * 60 * 60 * 24) 이걸 곱하면 일로 계산
				long onedayStamp = 1000 * 60 * 60 * 24; // 하루의 밀리세컨드 시간
				
				if (difference > onedayStamp) {
					System.out.println("최근 로그인 한 뒤 흐른 시간 : " + difference);
					addLoginPoint(uid); // 포인트를 1 증가(update()) 시키고 pointlog 내역에 insert()
				} else {
					System.out.println("최근 로그인 한지 하루가 안지남");
				}
				
				// 현재 시간을 테이블의 lastlogindate 컬럼에 update 시켜야 함
				updateLastLoginDate(uid);
			}
			
			
			// 로그인에 성공한 유저의 정보를 반환하기 위해 객체 생성
			loginMember = new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg"), 
					rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), lastlogindate);
		}
		
		System.out.println("DAO : " + loginMember);
		
		return loginMember;
	}
	
	private void updateLastLoginDate(String uid) throws NamingException, SQLException {
		// 현재 시간을 테이블의 lastlogindate 컬럼에 update 시켜야 함
		
		Connection con = DBManagement.getConnection();
		String query = "{call sp_updatelastlogindate(?)}";
		
		CallableStatement cstmt = con.prepareCall(query);
		cstmt.setString(1, uid);
		
		cstmt.execute();
		
		cstmt.close();
		con.close();
		
	}

	// 멤버의 포인트를 1 증가(update()) 시키고 pointlog 테이블에 포인트 내역 insert()
	private void addLoginPoint(String uid) throws NamingException, SQLException {
		
		Connection con = DBManagement.getConnection();
		String query = "{call sp_addloginpoint(?)}";
		
		CallableStatement cstmt = con.prepareCall(query);
		cstmt.setString(1, uid);
		
		cstmt.execute();
		
		cstmt.close();
		con.close();
	}

	// ------------- 회원 가입을 위한 메서드 -----------------------------------------------------------------------
	public void insertMember(MemberVO vo) throws NamingException, SQLException {
		boolean result = false;
		Connection con = DBManagement.getConnection();
		
		if (con != null) {
			String q = "{call sp_insertMemberVo(?, ?, ?)}";
			CallableStatement cstmt = con.prepareCall(q);
			
			cstmt.setString(1, vo.getUserid());
			cstmt.setString(2, vo.getUpwd());
			cstmt.setString(3, vo.getUserimg());
			
			cstmt.execute();
			
			
			cstmt.close();
			con.close();
		}
		
	}

	// --------------------------- 아이디 중복 검사 -------------------------------------------
	public boolean idIsDuplicate(String uid) throws NamingException, SQLException {
		boolean result = false;
		
		Connection con = DBManagement.getConnection();
		
		if (con != null) {
			
			String q = "{call idisduplicate(?, ?)}";
			
			CallableStatement cstmt = con.prepareCall(q);
			
			cstmt.setString(1, uid); // in 매개변수
			cstmt.registerOutParameter(2, java.sql.Types.NVARCHAR); // out 매개변수
			
			cstmt.execute();
			
			System.out.println(cstmt.getString(2));
			if (cstmt.getString(2).equals("true")) {
				result = true;
			}
//			result = Boolean.parseBoolean(cstmt.getString(2));
			
			cstmt.close();
			con.close();
		}
		
		return result;
	}

	public List<MemberVO> entireMember() throws NamingException, SQLException {
		List<MemberVO> members = new ArrayList<MemberVO>();
		
		Connection con = DBManagement.getConnection();
		String sql = "select * from membervo";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			members.add(new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg")
					, rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), rs.getDate("lastlogindate")));
		}
		
		return members;
		
	}

	// -------------- 한명의 회원 정보를 얻어와 MemberVO로 반환하는 메서드 -----------------------
	public MemberVO getMember(String uid) throws NamingException, SQLException {
		MemberVO member = null;
		Connection con = DBManagement.getConnection();
		String sql = "select * from membervo where userid=?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, uid);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			member = new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg")
					, rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), rs.getDate("lastlogindate"));
		}
		
		System.out.println("DAO :" + member);
		
		rs.close();
		pstmt.close();
		con.close();
		
		return member;
	}

	// ---------------- 회원의 포인트 내역을 반환하는 메서드 --------------------------------------
	public List<UserPointLog> getPointLog(String uid) throws NamingException, SQLException {
		List<UserPointLog> poinLog = new ArrayList<UserPointLog>();
		Connection con = DBManagement.getConnection();
		String sql = "select l.givendate, p.pointval, p.why from pointlog l, pointpolicy p where l.givenwhy = p.givenwhy and l.givenwho = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, uid);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			poinLog.add(new UserPointLog(rs.getDate("givendate"), rs.getInt("pointval"), rs.getString("why")));
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return poinLog;
	}
	
	
}
