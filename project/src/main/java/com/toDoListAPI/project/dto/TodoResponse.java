package com.toDoListAPI.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private LocalDateTime created_at;
}
