package com.example.demo.controller;

import com.example.demo.dto.UserBasicInfo;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while saving user: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();

            List<UserBasicInfo> userSummaries = users.stream()
                    .map(user -> new UserBasicInfo(
                            user.getId(),
                            user.getFullName(),
                            user.getMobileNumber(),
                            user.getLoanType().name(),   // Convert enum to string
                            user.getLoanStatus().name(), // Convert enum to string
                            user.getCreatedAt()
                    ))
                    .collect(Collectors.toList());
            Collections.reverse(userSummaries);
            return ResponseEntity.ok(userSummaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users: " + e.getMessage());
        }
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<?> updateLoanStatus(@PathVariable Long userId, @RequestParam String status) {
        try {
            userService.updateLoanStatus(userId, status.toUpperCase());
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Status changed successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error updating status: " + e.getMessage()));
        }
    }



}
