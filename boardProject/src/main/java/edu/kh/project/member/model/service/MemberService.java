package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.dto.Member;

public interface MemberService {

	Member login(Member inputMember);

	int signup(Member inputMember, String[] memberAddress);

	int checkEmail(String memberEmail);

	int checkNickname(String memberNickname);

	Member quickLogin(String memberEmail);
	
   /** 회원 목록 조회
    * @return memberList
    */
   List<Member> selectmemberList();

}
