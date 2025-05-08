package com.example.demo.dao;


import com.example.demo.model.Updates;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UpdatesRepository extends JpaRepository<Updates, Long> {
    Optional<Updates> findByUser(User user);

}
