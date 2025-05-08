package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    public enum LoanStatus {
        PENDING,
        APPROVED,
        REJECTED,

    }

    public enum LoanType {
        PERSONAL,
        HOME,
        EDUCATION,
        BUSINESS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank
    @Size(max = 15)
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @NotBlank
    @Size(max = 10)
    @Column(name = "pan_number", nullable = false)
    private String panNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false)
    private LoanStatus loanStatus = LoanStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "consent_id", nullable = false)
    private String consentId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private BankStatement bankStatement;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SummaryData summaryData;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Updates updates;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.loanStatus == null) {
            this.loanStatus = LoanStatus.PENDING;
        }
    }

    public User() {}

    public User(String fullName, String mobileNumber, String panNumber, String consentId, LoanType loanType) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.panNumber = panNumber;
        this.consentId = consentId;
        this.loanType = loanType;
        this.loanStatus = LoanStatus.PENDING;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }

    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPanNumber() { return panNumber; }

    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public LoanStatus getLoanStatus() { return loanStatus; }

    public void setLoanStatus(LoanStatus loanStatus) { this.loanStatus = loanStatus; }

    public LoanType getLoanType() { return loanType; }

    public void setLoanType(LoanType loanType) { this.loanType = loanType; }

    public String getConsentId() { return consentId; }

    public void setConsentId(String consentId) { this.consentId = consentId; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public BankStatement getBankStatement() { return bankStatement; }

    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
        if (bankStatement != null) {
            bankStatement.setUser(this);
        }
    }


    public Updates getUpdates() {
        return updates;
    }

    public void setUpdates(Updates updates) {
        this.updates = updates;
        if (updates != null) {
            updates.setUser(this);
        }
    }
}
