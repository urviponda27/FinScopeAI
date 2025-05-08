package com.example.demo.service.impl;

import com.example.demo.dto.CombinedUserBankDTO;
import com.example.demo.model.BankStatement;
import com.example.demo.model.User;
import com.example.demo.dao.BankStatementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.service.CombinedUserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;


@Service
public class CombinedUserBankServiceImpl implements CombinedUserBankService {

    private static final String BASE_UPLOAD_DIR = "uploads";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankStatementRepository bankStatementRepository;
    @Override
    @Transactional
    public User createUserWithBankStatement(CombinedUserBankDTO dto) {
        try {
            User newUser = buildUserFromDTO(dto);
            User savedUser = userRepository.save(newUser);

            MultipartFile pdfFile = dto.getPdfFile();
            validatePdf(pdfFile);

            Path pdfPath = savePdfToFileSystem(savedUser.getId(), pdfFile);

            BankStatement bankStatement = createBankStatement(savedUser, dto, pdfPath);
            bankStatementRepository.save(bankStatement);

            return savedUser; // ✅ Return the saved user
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Validation failed: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user and uploading bank statement. Please try again later.", e);
        }
    }

    @Override
    public ResponseEntity<Resource> getPdfAndConsentByUserId(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));

            BankStatement bankStatement = bankStatementRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("No bank statement found for user ID: " + userId));

            Resource pdfResource = getPdfResource(bankStatement);

            HttpHeaders headers = prepareResponseHeaders(user);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfResource);

        } catch (RuntimeException ex) {
            throw new RuntimeException("Unable to fetch PDF and user details: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error occurred while retrieving the PDF: " + ex.getMessage(), ex);
        }
    }

    private User buildUserFromDTO(CombinedUserBankDTO dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPanNumber(dto.getPanNumber());
        user.setConsentId(dto.getConsentId());
        user.setLoanStatus(User.LoanStatus.PENDING);
        user.setLoanType(User.LoanType.valueOf(dto.getLoanType().toUpperCase()));
        return user;
    }

    private void validatePdf(MultipartFile pdfFile) {
        if (pdfFile == null || pdfFile.isEmpty()) {
            throw new IllegalArgumentException("Uploaded PDF file is missing or empty.");
        }
    }

    private Path savePdfToFileSystem(Long userId, MultipartFile pdfFile) throws IOException {
        Path userDir = Paths.get(BASE_UPLOAD_DIR, "user_" + userId);
        Files.createDirectories(userDir);

        String originalFileName = pdfFile.getOriginalFilename();
        Path pdfPath = userDir.resolve(originalFileName);

        Files.copy(pdfFile.getInputStream(), pdfPath, StandardCopyOption.REPLACE_EXISTING);
        return pdfPath;
    }
    private BankStatement createBankStatement(User user, CombinedUserBankDTO dto, Path pdfPath) {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setUser(user);
        bankStatement.setAccountNumber(dto.getAccountNumber());
        bankStatement.setIfscCode(dto.getIfscCode());
        bankStatement.setPdfFilePath(pdfPath.toString());
        bankStatement.setUploadedAt(LocalDateTime.now());
        return bankStatement;
    }

    private Resource getPdfResource(BankStatement bankStatement) throws MalformedURLException {
        Path filePath = Paths.get(bankStatement.getPdfFilePath()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("The requested PDF file is not available or unreadable.");
        }
        return resource;
    }

    private HttpHeaders prepareResponseHeaders(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Consent-ID", user.getConsentId());
        headers.add("Loan-Type", user.getLoanType().name());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + Paths.get(user.getFullName()).getFileName() + "\"");
        return headers;
    }

    @Override
    public boolean doesUserExist(Long userId) {
        return userRepository.existsById(userId);
    }
}
