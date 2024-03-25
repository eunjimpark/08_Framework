package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;

@Controller  //빈등록
@RequestMapping("todo")
public class TodoController {

	@Autowired  //같은타입 빈 의존성주입
	private TodoService service;
	
	@PostMapping("add") // "/todo/add" POST 방식 요청 매핑
	public String addTodo(
		@RequestParam("todoTitle") String todoTitle,
		@RequestParam("todoContent") String todoContent,
		RedirectAttributes ra
		) {
		
		
		// RedirectAttributes : 리다이렉트 시 값을 1회성으로 전달하는 객체
		
		// RedirectAttributes.addFlashAttribute("key", value) 형식으로 잠깐 세션에 속성 추가
		
		// [원리]
		// 응답 전 : request scope
		// redirect 중 : session scope로 이동
		// 응답 후 : reqeust scope로 복귀
		
		
		
		// 서비스 메서드 호출 후 결과 반환 받기
		int result = service.addTodo(todoTitle, todoContent);
		
		// 삽입 결과에 따라 message 값 지정
		String message = null;
		
		if(result > 0)	message = "할 일 추가 성공!!!";
		else			message = "할 일 추가 실패...";
		
		//리다이렉트후 1회성으로 사용할 데이터를 속성으로추가
		ra.addFlashAttribute("message", message);
		
		return "redirect:/"; // 메인페이지 재요청
	}
	
	
	//상세조회
	@GetMapping("detail")
	public String todoDetail( 
			@RequestParam("todoNo") int todoNo, Model model,RedirectAttributes ra) {
		
		Todo todo = service.todoDetail(todoNo);
		
		String path = null;
		
		if(todo !=null) {//조회결과있을경우
			//templates/todo/detail.html
			path="todo/detail";  //포워드경로
			
			model.addAttribute("todo", todo);	
		}else { //조회결과없을경우
			path = "redirect:/"; //메인페이지로 리다이렉트	
			
			ra.addFlashAttribute("message","해당할일이없어요");
		}
		
		return path;
	}
	
	
	@GetMapping("delete")
	public String todoDelete(@RequestParam("todoNo") int todoNo,RedirectAttributes ra) {
		
			
		// 서비스 메서드 호출 후 결과 반환 받기
		int result = service.deleteTodo(todoNo);
			
		// 삽입 결과에 따라 message 값 지정
		String message = null;
			
		if(result > 0)	message = "할 일 삭제 성공!!!";
		else			message = "할 일 삭제 실패...";
			
		//리다이렉트후 1회성으로 사용할 데이터를 속성으로추가
		ra.addFlashAttribute("message", message);
			
		return "redirect:/"; // 메인페이지 재요청
			
	}
	
	/** 수정 화면 전환
	 * @param todoNo : 수정할 할 일 번호
	 * @param model : 데이터 전달 객체 (기본 : request)
	 * @return todo/update.html  forward
	 */
	@GetMapping("update")
	public String todoUpdate(
		@RequestParam("todoNo") int todoNo,
		Model model
		) {
		
		// 상세조회 서비스 호출 -> 수정화면에 출력할 이전 내용
		Todo todo = service.todoDetail(todoNo);
		
		model.addAttribute("todo", todo);
		
		return "todo/update";
	}
	
	
	@PostMapping("update")
	public String todoUpdate(
		@ModelAttribute Todo todo,
		RedirectAttributes ra) {
		
		// 수정 서비스 호출
		int result = service.todoUpdate(todo);
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) {
			// 상세 조회로 리다이렉트
			path += "/todo/detail?todoNo=" + todo.getTodoNo();
			message = "수정 성공!!!";
			
		} else {
			// 다시 수정 화면으로 리다이렉트
			path += "/todo/update?todoNo=" + todo.getTodoNo();
			message = "수정 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	
	/** 완료 여부 변경
	 * @param todo : 커맨드 객체 (@ModelAttribute 생략)
	 * 				- todoNo, complete 두 필드가 세팅된 상태 
	 * @param ra
	 * @return "detail?todoNo=" + 할 일 번호  (상대 경로)
	 */
	@GetMapping("changeComplete")
	public String  changeComplete (Todo todo, RedirectAttributes ra){
		
		//변경서비스 호출
		int result = service.changeComplete(todo);

		
		
		String message = null;
		
		if(result > 0) {
			// 상세 조회로 리다이렉트
			
			message = "변경 성공!!!";
			
		} else {
			// 다시 수정 화면으로 리다이렉트
			
			message = "변경 실패...";
		}
		
		//현재 요청 주소: /todo/changeComplete
		ra.addFlashAttribute("message",message);
		
		//응답주소 :      /todo/detail
		return "redirect:detail?todoNo="+todo.getTodoNo();
	}	
	
	
	
	
	
	
	
	
	
}
