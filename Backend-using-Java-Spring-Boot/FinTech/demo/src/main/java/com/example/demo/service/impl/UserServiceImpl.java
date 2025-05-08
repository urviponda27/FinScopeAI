package com.example.demo.service.impl;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user) {
        if (user.getBankStatement() != null) {
            user.getBankStatement().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public User updateLoanStatus(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        try {
            User.LoanStatus newStatus = User.LoanStatus.valueOf(status);
            user.setLoanStatus(newStatus);
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }


}
