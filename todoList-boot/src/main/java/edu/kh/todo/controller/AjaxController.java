package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;



/* [HttpMessageConverter]
 *  Spring에서 비동기 통신 시
 * - 전달되는 데이터의 자료형
 * - 응답하는 데이터의 자료형
 * 위 두가지 알맞은 형태로 가공(변환)해주는 객체
 * 
 * - 문자열, 숫자 <-> TEXT
 * - Map <-> JSON
 * - DTO <-> JSON
 * 
 * (참고)
 * HttpMessageConverter가 동작하기 위해서는
 * Jackson-data-bind 라이브러리가 필요한데
 * Spring Boot 모듈에 내장되어 있음
 * (Jackson : 자바에서 JSON 다루는 방법 제공하는 라이브러리)
 */



@Slf4j
@RequestMapping("ajax")
@Controller
public class AjaxController {
	
	@Autowired
	//등록된 빈중 같은 타입 또는 상속관계 빈을 해당필드에 의존성 주입
	private TodoService service;
	
	
	@GetMapping("main")
	public String ajaxMain() {
		return "ajax/main";
	}

	
	//전체 투두개수조회
	@ResponseBody   
	//컨트롤러 메서드의 반환값을 요청했떤 HTML/JS파일 부분에 값을 돌려 보내는역할
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		log.info("getTotalcount 메서드 호출됨!!!!");
		
	
		int totalCount = service.getTotalCount();
		
		return totalCount;
	}
	
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		return service.getCompleteCount();
	}
	

	@ResponseBody // 비동기 요청 결과로 값을 반환
	@PostMapping("add")
	public int addTodo(
// 		JSON이 파라미터로 전달된 경우 아래 방법으로 얻어오기 불가능
//		@RequestParam("todoTitle") String todoTitle,
//		@RequestParam("todoContent") String todoContent
			
		@RequestBody Todo todo // 요청 body에 담긴 값을 Todo에 저장
		) {
		
		log.debug(todo.toString());
		int result = service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
		return result;
	}
	
	
	
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		List<Todo> todoList=service.selectList();
		

		return todoList;
		
		// List(Java 전용 타입) 를 반환
		// -> JS가 인식할 수 없기 때문에
		//   HttpMessageConverter가 
		//  JSON형태 [{}, {}, {}] 로 변환하여 반환
		//	        (JSONArray)
		
		
		
	}
	
	
	// 할 일 상세 조회
	@ResponseBody // 요청한 곳으로 데이터 돌려보냄
	@GetMapping("detail")
	public Todo selectTodo( @RequestParam("todoNo") int todoNo ) {
		
		// return 자료형 : Todo
		// -> HttpMessageConverter가 String(JSON)형태로 변환해서 반환
		
		return service.todoDetail(todoNo);
	}
	
	
	
	@ResponseBody
	@DeleteMapping("delete")
	public int todoDelete(@RequestBody int todoNo) {
		return service.deleteTodo(todoNo);
	}
	
	
	@ResponseBody
	@PutMapping("changeComplete")
	public  int changeComplete(@RequestBody Todo todo) {
		return service.changeComplete(todo);
	}
	
	@ResponseBody
	@PutMapping("update")
	public int todoUpdate(@RequestBody Todo todo) {
		return service.todoUpdate(todo);
	}
	
	
}
















