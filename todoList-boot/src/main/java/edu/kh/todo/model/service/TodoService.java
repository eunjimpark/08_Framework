package edu.kh.todo.model.service;

import java.util.Map;

import edu.kh.todo.model.dto.Todo;



public interface TodoService {

	//할일목록+완료된할일개수조회
	Map<String, Object> selectAll();

	
	//할일추가
	int addTodo(String todoTitle, String todoContent);

	//할일ㅇ상세조회
	Todo todoDetail(int todoNo);


	//할일 삭제
	int deleteTodo(int todoNo);

	//할일수정
	int todoUpdate(Todo todo);


	int changeComplete(Todo todo);


	int getTotalCount();


	int getCompleteCount();
}
