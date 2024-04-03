package edu.kh.project.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;


@Transactional //해당클래스 메서드종료시까지 예외가발생하지않으면 commit 
                //중간에 예외발생하면 롤백
@Service
public class MemberServiceImpl implements MemberService{
	// 등록된 bean 중에서 같은 타입 또는 상속관계인 bean을
	// 자동으로 의존성 주입(DI)
	@Autowired
	private MemberMapper mapper;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 테스트(디버그 모드)
		
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환
		//String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		
		// 1. 이메일이 일치하면서 탈퇴하지 않은 회원 조회
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		// 2. 만약에 일치하는 이메일이 없어서 조회 결과가 null 인 경우
		if(loginMember == null) return null;
		
		// 3. 입력 받은 비밀번호(inputMember.getMemberPw()  (평문))와
		//    암호화된 비밀번호(loginMember.getMemberPw()) 
		//    두 비밀번호가 일치하는지 확인
		//일치하지않으면!
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		//로그인결과에서 비밀번호 제거
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
	
	// 회원 가입 서비스
	@Override
	public int signup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력되지 않으면
		// inputMember.getMemeberAddress()  -> ",,"
		// memberAddress -> [,,]
		
		
		// 주소가 입력된 경우!
		if( !inputMember.getMemberAddress().equals(",,")   ) {
			
			// String.join("구분자", 배열)
			// -> 배열의 모든 요소 사이에 "구분자"를 추가하여
			//   하나의 문자열로 만드는 메서드
			String address = String.join("^^^", memberAddress);
			// inputMember 주소로 합쳐진 주소를 세팅
			inputMember.setMemberAddress(address);
			
		}else {
			inputMember.setMemberAddress(null); // null 저장
		}
		
		//비밀번호암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		return mapper.signup(inputMember);
	}


	
	// 이메일 중복 검사
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}
	
	// 닉네임 중복 검사
	@Override
	public int checkNickname(String memberNickname) {
		return mapper.checkNickname(memberNickname);
	}
	
	
	
	
	// 빠른 로그인
	// -> 일반 로그인에서 비밀번호 비교만 제외
	@Override
	public Member quickLogin(String memberEmail) {
		
		Member loginMember = mapper.login(memberEmail);
		
		// 탈퇴 또는 없는 회원
		if(loginMember == null) return null;
		
		// 조회된 비밀번호 null로 변경
		loginMember.setMemberPw(null);
		return loginMember;
	}
	
	
   // 회원 목록 조회
   @Override
   public List<Member> selectmemberList(){
      
      return mapper.selectmemberList();
   }
	
	
	
	
	
}


/* BCrypt 암호화 (Spring Security 제공)
 * 
 * - 입력된 문자열(비밀번호)에 salt를 추가한 후 암호화
 *  -> 암호화 할 때 마다 결과가 다름
 *  
 *  ex) 1234 입력      -> $12!asdfg
 *  ex) 1234 다시 입력 -> $12!qwexs
 *  
 *  - 비밀번호 확인 방법
 *  	-> BCryptPasswordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
 * 			-> 평문 비밀번호와 
 *   		   암호화된 비밀번호가 같은 경우 true
 *   		   아니면 false 반환
 * 
 *  * 로그인 / 비밀번호 변경 / 탈퇴 등 비밀번호가 입력되는 경우
 *    DB에 저장된 암호화된 비밀번호를 조회해서
 *    matches() 메서드로 비교해야 한다!!
 * */
