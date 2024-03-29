package edu.kh.project.email.model.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final JavaMailSender mailSender;
	
	private final SpringTemplateEngine templateEngine;

	//필드에 의존성 주입하는 방법 권장X
	
	//@Autowired
	//private EmailService service;
	
	/*
	 * @Autowired를이용한 의존성주입 방법은 3가지
	 * 1)필드이용
	 * 2)세터 
	 * 3)생성자(권장)
	 * 
	 * Lombok라이브러리에서 제공하는 뤼카이얼알규컨스트럭처를 이용하면
	 * 필드중 
	 * 1)초기화되지않은 final이 붙은 필드
	 * 2)초기화되지않은 @notnull이 붙은 필드
	 * 
	 * 1,2에 해당하는 필드에 대한 오토와이얼드 생성자 구문을 자동완성
	 * */

	// 이메일 보내기
	@Override
	public String sendEmail(String htmlName, String email) {
		
		// 6자리 난수(인증 코드) 생성
		String authKey = createAuthKey();
		
		try {
			
			String subject = null;
			switch(htmlName) {
			case "signup" : subject = "[boardProject]회원가입 인증번호 입니다";
			                break;
			}
			
			//인증메일보내기
			//마임메세지 : 자바에서 메일을보내는 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			//마임메세지헬퍼:스프링에서 제공하는 메일발송도우미(간단+타임리프)
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email); //받는사람 이메일 지정
			helper.setSubject(subject); //이메일 제목지정
			//HTML코드 해석여부 true
			helper.setText(loadHtml(authKey,htmlName), true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));
		
			//메일보내기
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
		return authKey;
	}
	
	
	//HTML파일을 읽어와 스트링으로 변환
	public String loadHtml(String authKey, String htmlName) {
		Context context = new Context();
		
		
		//타임리프가 적용된 HTML에서 사용할값추가
		context.setVariable("authKey",authKey);
		
		//템플릿폴더에서 htmlName과같은 html파일내용을읽어와스트링으로변환
		return templateEngine.process("email/" + htmlName, context);
	}
	
	
	
	/**
	 * 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
	 * 
	 * @return authKey
	 */
	public String createAuthKey() {
		String key = "";
		for (int i = 0; i < 6; i++) {

			int sel1 = (int) (Math.random() * 3); // 0:숫자 / 1,2:영어

			if (sel1 == 0) {

				int num = (int) (Math.random() * 10); // 0~9
				key += num;

			} else {

				char ch = (char) (Math.random() * 26 + 65); // A~Z

				int sel2 = (int) (Math.random() * 2); // 0:소문자 / 1:대문자

				if (sel2 == 0) {
					ch = (char) (ch + ('a' - 'A')); // 대문자로 변경
				}

				key += ch;
			}

		}
		return key;
	}
	
	
	
}



