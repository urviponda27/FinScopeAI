package com.example.demo.controller;
import com.example.demo.model.BankStatement;
import com.example.demo.model.User;
import com.example.demo.service.BankStatementService;
import com.example.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/bank-statements")
@CrossOrigin(origins = "*")
public class BankStatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadBankStatement(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("ifscCode") String ifscCode    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        String basePath = "uploads";
        String userFolder = basePath + "/user_" + userId;
        File directory = new File(userFolder);
        if (!directory.exists() && !directory.mkdirs()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user directory.");
        }

        String fileName = file.getOriginalFilename();
        String filePath = userFolder + "/" + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store file: " + e.getMessage());
        }

        BankStatement statement = new BankStatement();
        statement.setUser(user);
        statement.setPdfFilePath(filePath);
        statement.setAccountNumber(accountNumber);
        statement.setIfscCode(ifscCode);

        bankStatementService.saveBankStatement(statement);

        return ResponseEntity.ok("PDF uploaded and saved successfully.");
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getBankStatementPdf(@PathVariable Long id) throws IOException {
        BankStatement statement = bankStatementService.getStatementById(id);

        Path filePath = Paths.get(statement.getPdfFilePath()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException("PDF not found: " + filePath);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                .body(resource);
    }
}
