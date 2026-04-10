package com.toDoListAPI.project.repository;

import com.toDoListAPI.project.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    // Custom queries for pagination/filtering later!
//    Page<Todo> findByUserId(Long userId, Pageable pageable);
}
