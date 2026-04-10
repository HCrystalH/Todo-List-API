package com.toDoListAPI.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="todo")
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id",nullable = false)
    private User user;  // FK

    @Column(name="is_completed",nullable = false)
    private Boolean isCompleted = false;   // default = false ( not completed)

    @Column(name="created_at",nullable = false)
    private LocalDateTime  createdAt;

    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;

    // Automating timestamp -> no need to manually set these when save a task
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
}
