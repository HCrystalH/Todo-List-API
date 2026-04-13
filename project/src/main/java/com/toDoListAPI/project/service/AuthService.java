package com.toDoListAPI.project.service;

import com.toDoListAPI.project.dto.JwtResponse;
import com.toDoListAPI.project.dto.LoginRequest;
import com.toDoListAPI.project.dto.RegisterRequest;
import com.toDoListAPI.project.entity.User;
import com.toDoListAPI.project.repository.UserRepository;
import com.toDoListAPI.project.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Transactional  // Because write data to DB
    public void registerUser(RegisterRequest request){
        String email = request.getEmail().trim().toLowerCase();
        // Check the email exist or not
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        // Email does not exist , then create
        User user = new User();
        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));    // hash password

        userRepository.save(user);  // save to DB
    }

    public JwtResponse login(LoginRequest request){
        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(password,user.getPassword())){
            return new JwtResponse(jwtUtils.generateToken(email));
        }else{
            throw new RuntimeException("Invalid credentials");
        }
    }
}
