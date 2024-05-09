package com.spring.todo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.todo.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByUserId(UUID id);
}
