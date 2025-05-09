package com.example.demo.controller;

import com.example.demo.dto.CombinedUserBankDTO;
import com.example.demo.model.User;
import com.example.demo.service.CombinedUserBankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/combined")
@CrossOrigin(origins = "*")
public class CombinedController {

    @Autowired
    private CombinedUserBankService combinedService;

    @PostMapping("/register")
    public ResponseEntity<Long> createUserAndBankStatement(@ModelAttribute @Valid CombinedUserBankDTO dto) {
        MultipartFile pdfFile = dto.getPdfFile();
        if (pdfFile == null || pdfFile.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User savedUser = combinedService.createUserWithBankStatement(dto);
            return ResponseEntity.ok(savedUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdf-consent/{userId}")
    public ResponseEntity<?> getPdfAndConsentByUserId(@PathVariable Long userId) {
        try {
            if (!combinedService.doesUserExist(userId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + userId);
            }

            return combinedService.getPdfAndConsentByUserId(userId);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error fetching bank statement: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred while retrieving the PDF: " + ex.getMessage());
        }
    }
}
