package com.spring.todo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.todo.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUserName(String username);
}
