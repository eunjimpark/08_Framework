package edu.kh.todo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.mapper.TodoMapper;

@Service // 비즈니스 로직(데이터 가공, 트랜잭션 처리) 역할 명시
		 // + Bean 등록
public class TodoServiceImpl implements TodoService{
	
	@Autowired
	private TodoMapper mapper;
	// 할 일 목록 + 완료된 할 일 개수 조회
	@Override
	public Map<String, Object> selectAll() {
				
		//1.할일목록조회
		List<Todo> todoList = mapper.selectAll(); 
		
		//2.완라ㅛ된 할일 개수 조회
		int completeCount = mapper.getCompleteCount();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("todoList", todoList);
		map.put("completeCount", completeCount); 
		
		
		return map;
	}
	
	
	
	// 할 일 추가
	@Override
	public int addTodo(String todoTitle, String todoContent) {
		
		// Connection 생성/반환 X
		// 트랜잭션 제어 처리 -> @Transactional 어노테이션
		
		// 마이바티스에서 SQL에 전달할 수 있는 파라미터의 개수는
		// 오직 1개!!!
		
		Todo todo = new Todo();
		todo.setTodoTitle(todoTitle);
		todo.setTodoContent(todoContent);
		
		
		return mapper.addTodo(todo);
	}
	
	
}
