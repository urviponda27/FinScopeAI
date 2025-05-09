package com.example.demo.controller;

import com.example.demo.dao.SummaryDataRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.FilteredSummaryDataDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SummaryData;
import com.example.demo.model.User;
import com.example.demo.service.SummaryDataService;
import com.example.demo.service.UserService;
// import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "*")
public class SummaryDataController {

    private final SummaryDataService summaryDataService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final SummaryDataRepository summaryDataRepository;

    @Autowired
    // Constructor-based dependency injection
    public SummaryDataController(SummaryDataService summaryDataService, UserService userService,
            UserRepository userRepository, SummaryDataRepository summaryDataRepository) {
        this.summaryDataService = summaryDataService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.summaryDataRepository = summaryDataRepository;
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> saveSummaryData(@PathVariable Long userId, @RequestBody SummaryData summaryData) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        summaryData.setUser(user);

        SummaryData savedData = summaryDataService.saveSummaryData(summaryData);
        return ResponseEntity.ok(savedData);
    }

    // Get all SummaryData - GET request
    @GetMapping("/getAll")
    public ResponseEntity<List<SummaryData>> getAllSummaryData() {
        // Retrieve the list of all SummaryData
        List<SummaryData> summaryDataList = summaryDataService.getAllSummaryData();
        // Return the list with a status of 200 (OK)
        return ResponseEntity.ok(summaryDataList);
    }

    // Get SummaryData by ID - GET request
    @GetMapping("/{id}")
    public ResponseEntity<SummaryData> getSummaryById(@PathVariable Long id) {
        // Retrieve SummaryData by ID
        SummaryData summaryData = summaryDataService.getSummaryDataById(id);
        // Return the data with a status of 200 (OK)
        return ResponseEntity.ok(summaryData);
    }

    // Get SummaryData by User ID - GET request
    @GetMapping("/user/{userId}")
    public ResponseEntity<FilteredSummaryDataDTO> getSummaryByUserId(@PathVariable Long userId) {
        FilteredSummaryDataDTO dto = summaryDataService.getSummaryByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    // Global Exception Handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Return a 404 response with the exception message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
