package edu.kh.project.myPage.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int updateInfo(Member inputMember, String[] memberAddress);

	int changePw(String newPw, String currentPw, int memberNo);

	int deleteMember(String currentPw, int memberNo);

	String fileUpload1 (MultipartFile uploadFile)throws IllegalStateException, IOException;

	
	/** 파일 업로드 테스트2(+DB)
	 * @param uploadFile
	 * @param memberNo
	 * @return result
	 */
	int fileUpload2(MultipartFile uploadFile, int memberNo)
			throws IllegalStateException, IOException ;

	
	
	
	List<UploadFile> fileList();

	
	/** 여러 파일 업로드
	 * @param aaaList
	 * @param bbbList
	 * @param memberNo
	 * @return
	 */
	int fileUpload3(
		List<MultipartFile> aaaList, 
		List<MultipartFile> bbbList, 
		int memberNo) throws IllegalStateException, IOException ;

	
	
	
	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	int profile(MultipartFile profileImg, Member loginMember) 
			throws IllegalStateException, IOException;

}

