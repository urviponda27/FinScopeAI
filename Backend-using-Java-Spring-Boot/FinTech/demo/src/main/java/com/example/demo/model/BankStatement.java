package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_statements")
public class BankStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_user_id"))
    @JsonBackReference
    private User user;

    @NotBlank(message = "PDF file path must not be empty.")
    @Size(max = 255, message = "PDF file path must be at most 255 characters.")
    @Column(name = "pdf_file_path", nullable = false, length = 255)
    private String pdfFilePath;

    @NotBlank(message = "Account number must not be empty.")
    @Size(max = 30, message = "Account number must be at most 30 characters.")
    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;

    @NotBlank(message = "IFSC code must not be empty.")
    @Size(max = 20, message = "IFSC code must be at most 20 characters.")
    @Column(name = "ifsc_code", nullable = false, length = 20)
    private String ifscCode;

    @NotNull
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    public BankStatement() {}

    public BankStatement(User user, String pdfFilePath, String accountNumber, String ifscCode) {
        this.user = user;
        this.pdfFilePath = pdfFilePath;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getPdfFilePath() { return pdfFilePath; }

    public void setPdfFilePath(String pdfFilePath) { this.pdfFilePath = pdfFilePath; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIfscCode() { return ifscCode; }

    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }


    public LocalDateTime getUploadedAt() { return uploadedAt; }

    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
