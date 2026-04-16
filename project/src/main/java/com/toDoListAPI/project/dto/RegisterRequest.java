package com.toDoListAPI.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message="Please provide your name")
    private String name;

    @NotBlank(message = "Please provide your email")
    private String email;

    @NotBlank(message ="Your password can't empty")
    private String password;
}
