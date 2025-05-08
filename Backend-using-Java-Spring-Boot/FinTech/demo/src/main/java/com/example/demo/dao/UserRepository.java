package com.example.demo.dao;



import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByPanNumber(String panNumber);
    Optional<User> findByConsentId(String consentId);
}
