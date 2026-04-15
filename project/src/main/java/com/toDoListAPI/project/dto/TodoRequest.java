package com.toDoListAPI.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TodoRequest {
    @NotBlank(message ="Title is required")
    private String title;
    private String description;
}
