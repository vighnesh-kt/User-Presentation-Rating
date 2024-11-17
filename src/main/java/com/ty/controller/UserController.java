package com.ty.controller;

import com.ty.dto.ResponseStructure;
import com.ty.entity.User;
import com.ty.enums.Status;
import com.ty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new use
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<User>> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Update user status
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseStructure<Status>> updateStatus(@PathVariable Integer id, @RequestParam Status status) {
        return userService.updateStatus(id, status);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<User>> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Get all users (admin)
    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<java.util.List<User>>> getAllUsers() {
        return userService.getAllUsers();
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<User>> loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }
}
