package com.spring.todo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spring.todo.dtos.TodoDto;
import com.spring.todo.entities.Todo;
import com.spring.todo.entities.User;
import com.spring.todo.repositories.TodoRepository;

@Service
public class TodoService {

	private final TodoRepository todoRepository;
	
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
	public List<Todo> getAllTodos(UUID userId) {
		List<Todo> todoList = new ArrayList<>();
		this.todoRepository.findByUserId(userId).stream().forEach(item -> todoList.add(item));
		return todoList;
	}
	
	public Todo getTodoById(Long id) {
		Optional<Todo> todoOptional = this.todoRepository.findById(id);
		if (todoOptional.isPresent()) {
			return todoOptional.get();
		}
		throw new IllegalStateException("Todo doesn't exist");
	}
	
	public Todo saveNewTodo(TodoDto todoRequest) {
		Todo newTodo = new Todo();
		newTodo.setTitle(todoRequest.getTitle());
		newTodo.setDescription(todoRequest.getDescription());
		newTodo.setIsFinished(false);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		newTodo.setUser(currentUser);
		return this.todoRepository.save(newTodo);
	}
	
	public Todo updateTodo(Long id, TodoDto todoRequest) {
		Optional<Todo> todoOptional = this.todoRepository.findById(id);
		if (todoOptional.isPresent()) {
			Todo currentTodo = todoOptional.get();
			if (todoRequest.getTitle() != null && !todoRequest.getTitle().isEmpty() && !todoRequest.getTitle().equals(currentTodo.getTitle())) {
				currentTodo.setTitle(todoRequest.getTitle());
			}
			if (todoRequest.getDescription() != null && !todoRequest.getDescription().isEmpty() && !todoRequest.getDescription().equals(currentTodo.getDescription())) {
				currentTodo.setDescription(todoRequest.getDescription());
			}
			return this.todoRepository.save(currentTodo);
		}
		throw new IllegalStateException("Todo doesn't exist");
	}
	
	public Todo deleteTodo(Long id) {
		Optional<Todo> todoOptional = this.todoRepository.findById(id);
		if (todoOptional.isPresent()) {
			this.todoRepository.deleteById(id);
			return todoOptional.get();
		}
		throw new IllegalStateException("Todo doesn't exist");
	}
}
