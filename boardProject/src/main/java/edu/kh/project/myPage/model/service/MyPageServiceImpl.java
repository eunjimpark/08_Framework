package edu.kh.project.myPage.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	
	
	private final  BCryptPasswordEncoder bcrypt;
	
	// @RequiredArgsConstructor 를 이용했을 때 자동 완성 되는 구문
//	@Autowired
//	public MyPageServiceImpl(MyPageMapper mapper) {
//		this.mapper = mapper;
//	}
	
	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 입력된 주소가 있을 경우 
		// memberAddress를 A^^^B^^^C 형태로 가공
		
		// 주소 입력 X -> inputMember.getMemberAddress()  -> ",,"
		if( inputMember.getMemberAddress().equals(",,") ) {
			
			// 주소에 null 대입
			inputMember.setMemberAddress(null);
		
		} else { 
			//  memberAddress를 A^^^B^^^C 형태로 가공
			String address = String.join("^^^", memberAddress);
			
			// 주소에 가공된 데이터 대입
			inputMember.setMemberAddress(address);
		}
		
		// SQL 수행 후 결과 반환
		return mapper.updateInfo(inputMember);
	}
	
	@Override
	public int changePw(String newPw, String currentPw, int memberNo) {
//		- bcrypt 암호화된 비밀번호를  DB에서 조회(회원 번호 필요)
		String encPw = mapper.selectPw(memberNo);
		
		//현재 비밀번호 == bcrypt 암호화된 비밀번호 비교
		//(  BcryptPasswordEncoder.matches(평문, 암호화된 비밀번호)   )
		if(!bcrypt.matches(currentPw, encPw)) {//다를때
			return 0;
			
		}
		
		/*- 같을 경우
		 -> 새 비밀번호를 암호화 진행
		 
		 -> 새 비밀번호로 변경(UPDATE)하는 Mapper 호출
		    회원 번호, 새 비밀번호 -> 하나로 묶음 (Member 또는 Map) 
		    -> 결과 1 또는 0 반환*/

		String pw = bcrypt.encode(newPw);
		
		Map<String, Object> pack = new HashMap<>();
		
		pack.put("memberNo", memberNo);
		pack.put("pw", pw);
		
		return mapper.changePw(pack);
	}
}





