package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//instacne 개발자가 만들고 관리하는객체

//Bean  스프링이만들고 관리하는객체
@Controller
public class ExampleController {
	
	/*요청 주소 매핑하는 방법
	 * 1)@RequestMapping("주소")
	 * 2)@GetMapping("주소") : GET 조회방식 요청매핑
	 *   @PostMapping("주소")  Post 삽입방식 요청매핑
	 *   @PutMapping("주소") : put 수정 방식 요청매핑
	 *   @DeleteMapping  : 딜리트 삭제방식 요청매핑
	 *   */

	@GetMapping("example")  
	public String exampleMethod() {
		//포워드하려는 html파일 경로작성
		//단, 뷰리졸버가제공하는 타임리프접두사,접미사제외하고작성
		return "example";
	}

}
