-- 회원가입시 회원테이블에 아이디, 비밀번호를 insert하고, point를 100지급(default), 포인트 지급내역을 pointlog에 남김
create or replace procedure sp_insertMemberVo
(
    userid in nvarchar2,
    upwd in nvarchar2,
    userimg in nvarchar2
)
is

begin
    insert into membervo(userid, upwd, userimg) values(userid, upwd, userimg); -- 회원가입
    insert into pointlog(givenwho, givenwhy) values(userid, 'A');
end;

-- 아이디가 중복인지 아닌지 갯수를 반환 하는 저장프로시져
create or replace procedure idisduplicate
(
    uid in membervo.userid%type,
    outmsg out nvarchar2
)
is
    vcnt number(1) := 0;
begin
    select count(*) into vcnt from membervo where userid=uid;
    
    if vcnt = 1 then
        outmsg := 'true';
    else 
        outmsg := 'false';
    end if;
end;
