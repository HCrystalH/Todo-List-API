package com.toDoListAPI.project.repository;

import com.toDoListAPI.project.entity.Todo;
import com.toDoListAPI.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    // Custom queries for pagination/filtering later!
    // using Pageable to support paging -> Spring will automatically handle LIMIT and OFFSET
    Page<Todo> findByUser(User user, Pageable pageable);
}
