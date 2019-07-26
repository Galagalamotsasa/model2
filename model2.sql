-- 회원 기본 테이블 
CREATE TABLE "SCOTT"."MEMBERVO" 
   (	
   	"USERID" NVARCHAR2(10) NOT NULL ENABLE,  -- 아이디
	"UPWD" NVARCHAR2(12) NOT NULL ENABLE,  -- 비밀번호
	"REGISTERDATE" DATE DEFAULT sysdate,  -- 가입일
	"ISADMIN" CHAR(2 BYTE) DEFAULT 'n',  -- 회원 / 관리자 구분
	"POINT" NUMBER(5,0) DEFAULT 100,     -- 포인트
	 CONSTRAINT "MEMBERVO_PK" PRIMARY KEY ("USERID")   -- userid PK
  
   );
   
 -- point정책 테이블  : 누구에게 왜 몇 포인트를 지급하는가?
 create table pointpolicy(
givenwhy char(1) primary key, -- 왜? pointlog 테이블이 참조
pointval number(3), -- 가감 및 차감 포인트
why nvarchar2(50));  -- 포인트 가감 및 차감 이유

-- point 지급 / 사용 내역 테이블
create table pointlog(
givendate date default sysdate, -- 언제
givenwho nvarchar2(10) references membervo(userid), -- 누구에게
givenwhy char(1) references pointpolicy(givenwhy)); -- 포인트를 줬다 이유. 
