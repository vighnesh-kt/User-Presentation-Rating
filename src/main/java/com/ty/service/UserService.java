package com.ty.service;

import com.ty.dto.ResponseStructure;
import com.ty.entity.User;
import com.ty.enums.Status;
import com.ty.exception.UserNotFoundException;
import com.ty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    
    public ResponseEntity<ResponseStructure<User>> registerUser(User user) {
        ResponseStructure<User> response = new ResponseStructure<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            response.setStatusCode(HttpStatus.CONFLICT.value());
            response.setMessage("Email already exists!");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        user.setStatus(Status.ACTIVE); // Default status
        User savedUser = userRepository.save(user);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User registered successfully");
        response.setData(savedUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update user status
    @Transactional
    public ResponseEntity<ResponseStructure<Status>> updateStatus(Integer userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        user.setStatus(status);
        User updatedUser = userRepository.save(user);

        ResponseStructure<Status> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User status updated successfully");
        response.setData(updatedUser.getStatus());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get user by ID
    public ResponseEntity<ResponseStructure<User>> getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User found");
        response.setData(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all users (admin)
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        List<User> users = userRepository.findAll();

        ResponseStructure<List<User>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All users retrieved successfully");
        response.setData(users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Login user
    public ResponseEntity<ResponseStructure<User>> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        ResponseStructure<User> response = new ResponseStructure<>();

        if (user == null || !user.getPassword().equals(password)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid email or password");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Login successful");
        response.setData(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
