/*계정생성 관리자계정으로접속*/
ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;

CREATE USER SPRING_PEJ IDENTIFIED BY SPRING1234;

GRANT CONNECT, RESOURCE TO SPRING_PEJ;

ALTER USER SPRING_PEJ
DEFAULT TABLESPACE USERS 
QUOTA 20M ON USERS;

-->계정 생성 후 접속 방법/새데이터베이스추가
------------------------------------------------------
/*스프링계정 접속*/
--"" : 내부에작성된글모양 그대로인식 ->대소문자구분
-->""작성권장

--CHAR (10)    :고정길이문자열 10바이트  최대 2000바이트
--VARCHAR2(10):가변길이 문자열 10바이트  최대 4000바이트
--NVARCHAR2(10) : 가변길이 문자열 10글자 (최대4000바이트)
--CLOB : 가변길이 문자열 (최대 4GB)

DROP TABLE "MEMBER";

/*멤버테이블생성*/
CREATE TABLE "MEMBER"(
	"MEMBER_NO" NUMBER CONSTRAINT "MEMEBER_PK" PRIMARY KEY,
	"MEMBER_EMAIL" NVARCHAR2(50) NOT NULL,
	"MEMBER_PW" NVARCHAR2(100) NOT NULL,
	"MEMBER_NICKNAME" NVARCHAR2(10)  NOT NULL,
	"MEMBER_TEL"  CHAR(11) NOT NULL, 
	"MEMBER_ADDRESS" NVARCHAR2(150),
	"PROFILE_IMG" VARCHAR2(300),
	"ENROLL_DATE"     DATE  DEFAULT SYSDATE NOT NULL,
	"MEMBER_DEL_FL" CHAR(1) DEFAULT 'N'
	                 CHECK("MEMBER_DEL_FL" IN ('Y','N')),
	"AUTHORITY"  NUMBER DEFAULT 1
	             CHECK("AUTHORITY" IN (1,2))
 );
	               



--회원번호 시퀀스 만들기
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;

-- 샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 
			 'member01@kh.or.kr',
			 '$2a$10$zlLWoMIOyjbMtsYCEe5Fr.s1uPNGN7tNEp/QUEenS2Hd51hFR0ydC',
			 '샘플1',
			 '01012341234',
			 NULL,
			 NULL,
			 DEFAULT,
			 DEFAULT,
			 DEFAULT
);

COMMIT;


SELECT * FROM "MEMBER";




-- 로그인
-- -> BCrypt 암호화 사용 중
-- -> DB에서 비밀번호 비교 불가능!!!
-- -> 그래서 비밀번호(MEMBER_PW)를 조회\

-- --> 이메일이 일치하는 회원 + 탈퇴 안한 회원 조건만 추가

SELECT MEMBER_NO, MEMBER_EMAIL, MEMBER_NICKNAME , MEMBER_PW,
			MEMBER_TEL, MEMBER_ADDRESS, PROFILE_IMG, AUTHORITY,
			TO_CHAR(ENROLL_DATE, 
				'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초"') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_EMAIL = ?
AND MEMBER_DEL_FL = 'N'

















