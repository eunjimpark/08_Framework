package edu.kh.todo.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.todo.model.dto.Todo;

/* @Mapper
 * - Mybatis 제공 어노테이션
 * - 해당 어노테이션 작성된 인터페이스는
 *   namespace에 해당 인터페이스가 작성된
 *   mapper.xml파일과 연결되어 SQL 호출/수행/결과 반환 가능
 * */

@Mapper
public interface TodoMapper {

	//할일목록조회
	List<Todo> selectAll();
	
	
	//완료된 할일 개수 조회
	int getCompleteCount();

	//할일추가
	int addTodo(Todo todo);

	
	
}

