package com.example.demo.service;

import com.example.demo.model.SummaryData;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User updateLoanStatus(Long userId, String status);
}

