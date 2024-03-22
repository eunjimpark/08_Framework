package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//Controller :요청에따라 알맞은서비스 호출
//				+서비스결과에 따라 어떤응답을 할지 제어

				
@Controller  //요청이랑응답제어 역할 명시 + Bean으로등록				
public class MainController {

	
	//메인페이지 지정시에는 "/"작성 가능
	@RequestMapping("/")
	public String mainPage() {
		
		//forward:요청위임
		//thymeleaf : Spring Boot에서 사용하는 템플릿엔진

		//타임리프를 이용한 html파일로 포워드시 사용되는 접두사,접미사존재
		//접두사 : classpath:/templates/
		//접미사 : .html
		
		return "common/main";
		
	}
	
	
}
