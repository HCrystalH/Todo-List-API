package com.toDoListAPI.project.service;

import com.toDoListAPI.project.dto.TodoRequest;
import com.toDoListAPI.project.dto.TodoResponse;
import com.toDoListAPI.project.entity.Todo;
import com.toDoListAPI.project.entity.User;
import com.toDoListAPI.project.repository.TodoRepository;
import com.toDoListAPI.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoResponse createTodo(TodoRequest toRequest){
        User user = getCurrentUser();
        Todo todo = new Todo();

        todo.setTitle(toRequest.getTitle());
        todo.setDescription(toRequest.getDescription());
        todo.setUser(user);

        // Link and save
        Todo saved = todoRepository.save(todo);
        return mapToResponse(saved);
    }

    public List<TodoResponse> getMyTodos(int page, int size,String sortBy){
        User user = getCurrentUser();

        // Create a page request: page 0 ,size 10, sorted by created_at descending
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        return todoRepository.findByUser(user,pageable)  // return list of Todo object
                .stream()                       // Convert  to pipeline to process each one by one
                .map(this::mapToResponse)       // transform each element Todo -> TodoResponse by mapToResponse method
                .toList();                      // to List<ToDoResponse>
        /*
            Equivalent one:
            List<Todo> todos = todoRepository.findByUser(user);
            List<TodoResponse> result = new ArrayList<>;
            for(Todo todo : todos)  result.add(mapToResponse(todo));
            return result;
         */
    }

    public TodoResponse updateStatus(Long id){
        User user = getCurrentUser();
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo Not Found")
        );

        // Check whether todo belong to the owner ?
        if(!todo.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to update this task");
        }

        todo.setIsCompleted(!todo.getIsCompleted());
        todo.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(todoRepository.save(todo));
    }

    @Transactional
    public void deleteTodo(Long id){
        User user  = getCurrentUser();
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Todo Not Found")
        );

        if(!todo.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to delete this task");
        }

        todoRepository.delete(todo);
    }

    // Helper methods
    private User getCurrentUser(){
        // Get current user's email from Security Context
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new RuntimeException("User Not Found"));
    }

    private TodoResponse mapToResponse(Todo todo){
        // Must match with TodoResponse in DTO
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getIsCompleted(),
                todo.getCreatedAt()
                );
    }
}
