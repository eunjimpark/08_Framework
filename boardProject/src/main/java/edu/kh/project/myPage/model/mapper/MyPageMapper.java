package edu.kh.project.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

@Mapper
public interface MyPageMapper {

	/** 회원 정보 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

	
	//암호화된비번조회
	String selectPw(int memberNo);


	//새비번변경
	int changePw(Map<String, Object> pack);

	//회원탈퇴
	int deleteMember(int memberNo);


	int putPw(Map<String, Object> map);

	
	/** 파일 정보를 DB에 삽입
	 * @param uf
	 * @return result
	 */
	int insertUploadFile(UploadFile uf);


	
	List<UploadFile> fileList();
	
	
	/** 프로필 이미지 변경
	 * @param mem
	 * @return result
	 */
	int profile(Member mem);






}
