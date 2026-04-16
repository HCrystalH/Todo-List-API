package com.toDoListAPI.project.service;

import com.toDoListAPI.project.entity.Todo;
import com.toDoListAPI.project.entity.User;
import com.toDoListAPI.project.repository.TodoRepository;
import com.toDoListAPI.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)     // Tells JUnit to use Mockito
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks            // Automatically injects the mocks above into the service
    private TodoService todoService;

    @Test
    void deleteTodo_ShouldThrowException_WhenUserIsNotOwner(){
        // --- ARRANGE ---
        User userA = new User();
        userA.setId(1L);
        userA.setEmail("user_a@test.com");

        User userB = new User();
        userB.setId(2L);

        Todo todoOfUserB = new Todo();
        todoOfUserB.setId(100L);
        todoOfUserB.setUser(userB);

        // Mock the Security Context to act like User A is logged in
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("user_a@test.com");
        when(userRepository.findByEmail("user_a@test.com")).thenReturn(Optional.of(userA));

        // Mock the repository to find User B Todo
        when(todoRepository.findById(100L)).thenReturn(Optional.of(todoOfUserB));

        // ACT & ASSERT
        // We expect a RuntimeException because User A is trying to delete User B's Todo
        RuntimeException exception = assertThrows(RuntimeException.class, () ->{
            todoService.deleteTodo(100L);
        });

        assertEquals("You are not allowed to delete this task",exception.getMessage());
//        assertEquals("Todo Not Found",exception.getMessage());
        // Verify that delete was NEVER actually called on the Database
        verify(todoRepository,never()).delete(any());
    }
}
