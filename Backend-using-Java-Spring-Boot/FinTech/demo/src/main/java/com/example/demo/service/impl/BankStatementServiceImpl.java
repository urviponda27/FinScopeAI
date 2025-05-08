package com.example.demo.service.impl;

import com.example.demo.model.BankStatement;
import com.example.demo.model.User;
import com.example.demo.dao.BankStatementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.service.BankStatementService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankStatementServiceImpl implements BankStatementService {

    @Autowired
    private BankStatementRepository bankStatementRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public BankStatement saveBankStatement(BankStatement bankStatement) {
        Long userId = bankStatement.getUser().getId();
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Ensure that the user is set in the bank statement
        bankStatement.setUser(existingUser);

        // Since this is a OneToOne relationship, ensure no other bank statement exists for this user
        // If you need to enforce this uniqueness, you can add additional validation here

        return bankStatementRepository.save(bankStatement);
    }

    @Override
    public List<BankStatement> getAllStatements() {
        return bankStatementRepository.findAll();
    }

    @Override
    public BankStatement getStatementByUserId(Long userId) {
        // Since it's a OneToOne relationship, you should retrieve only one statement for the user
        return bankStatementRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No bank statement found for user with id: " + userId));
    }

    @Override
    public BankStatement getStatementById(Long id) {
        return bankStatementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank statement not found with id: " + id));
    }
}
