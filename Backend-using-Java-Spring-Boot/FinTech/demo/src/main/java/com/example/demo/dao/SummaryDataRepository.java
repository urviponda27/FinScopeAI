package com.example.demo.dao;

import com.example.demo.model.SummaryData;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummaryDataRepository extends JpaRepository<SummaryData, Long> {

    Optional<SummaryData> findByUserId(Long userId); // already correct

    // Correct method to find by User entity
    Optional<SummaryData> findByUser(User user); // This works as the field name is 'user' in SummaryData
}
