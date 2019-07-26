package com.goodee.controller;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.goodee.dto.MemberVO;
import com.goodee.dto.UserPointLog;

public interface IActionFactory {
	// 회원가입 - 테이블에 회원객체를 인서트 한다. insert작업, 
	void registerMember(MemberVO vo) throws NamingException, SQLException;
	
	// 회원가입시 중복되는 회원인지 검사
	boolean idIsDuplicate(String uid) throws NamingException, SQLException;
	
	// 로그인 처리 
	MemberVO loginProcess(String uid, String pwd) throws NamingException, SQLException;
	
	// 전체 회원 정보를 반환하는 메서드 select
	List<MemberVO> entireMember() throws NamingException, SQLException;
	
	// 한명의 회원 정보를 반환하는 메서드 
	MemberVO getMember(String uid) throws NamingException, SQLException;
	
	// 회원의 포인트 로그를 반환하는 메서드
	List<UserPointLog> getPointLog(String uid) throws NamingException, SQLException;
	
}
