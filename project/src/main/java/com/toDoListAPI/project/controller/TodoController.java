package com.toDoListAPI.project.controller;


import com.toDoListAPI.project.dto.TodoRequest;
import com.toDoListAPI.project.dto.TodoResponse;
import com.toDoListAPI.project.service.TodoService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    // Constructor injection
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid  @RequestBody TodoRequest request){
        return ResponseEntity.ok(todoService.createTodo(request));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        return ResponseEntity.ok(todoService.getMyTodos(page,size,sortBy));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TodoResponse> updateStatus(@PathVariable @Nonnull Long id){
        return ResponseEntity.ok(todoService.updateStatus(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable @Nonnull Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Successfully Deleted Todo");
    }
}
