package com.toDoListAPI.project.repository;

import com.toDoListAPI.project.entity.Todo;
import com.toDoListAPI.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    // Custom queries for pagination/filtering later!
    List<Todo> findByUser(User user);
}
