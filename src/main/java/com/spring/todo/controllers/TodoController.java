package com.spring.todo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.dtos.TodoDto;
import com.spring.todo.entities.Todo;
import com.spring.todo.entities.User;
import com.spring.todo.services.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	final private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Todo>> getAllTodos() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		List<Todo> todoList = this.todoService.getAllTodos(currentUser.getId());
		return ResponseEntity.ok(todoList);
	}
	
	@GetMapping("/{todoId}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("todoId") Long todoId) {
		Todo foundTodo = this.todoService.getTodoById(todoId);
		return ResponseEntity.ok(foundTodo);
	}
	
	@PostMapping("/")
	public ResponseEntity<Todo> addTodo(@RequestBody TodoDto todoRequest) {
		Todo newTodo = this.todoService.saveNewTodo(todoRequest);
		return ResponseEntity.ok(newTodo);
	}
	
	@PutMapping("/{todoId}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoDto todoRequest) {
		Todo updatedTodo = this.todoService.updateTodo(todoId, todoRequest);
		return ResponseEntity.ok(updatedTodo);
	}
	
	@DeleteMapping("/{todoId}")
	public ResponseEntity<Todo> deleteTodo(@PathVariable("todoId") Long todoId) {
		Todo deletedTodo = this.todoService.deleteTodo(todoId);
		return ResponseEntity.ok(deletedTodo);
	}
}
