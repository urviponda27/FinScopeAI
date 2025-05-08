package com.example.demo.dao;

import com.example.demo.model.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
    // Custom query to find a BankStatement by User ID (for one-to-one relationship)
    Optional<BankStatement> findByUserId(Long userId);
}
