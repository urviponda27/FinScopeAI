package com.example.demo.service;

import com.example.demo.model.BankStatement;

import java.util.List;

public interface BankStatementService {
    BankStatement saveBankStatement(BankStatement bankStatement);
    List<BankStatement> getAllStatements(); // Returns all bank statements
    BankStatement getStatementByUserId(Long userId); // Fetches the bank statement by userId (one-to-one relationship)
    BankStatement getStatementById(Long id); // Fetches a bank statement by its id
}
