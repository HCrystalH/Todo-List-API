package com.toDoListAPI.project.repository;

import com.toDoListAPI.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /* An interface can inherit from another interface*/

    // For login/registration checking
    Optional<User> findByEmail(String email);
}
