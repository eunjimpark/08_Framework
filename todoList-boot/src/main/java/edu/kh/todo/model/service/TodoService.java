package edu.kh.todo.model.service;

import java.util.Map;

public interface TodoService {

	//할일목록+완료된할일개수조회
	Map<String, Object> selectAll();

	
	//할일추가
	int addTodo(String todoTitle, String todoContent);
}
