package com.example.demo.dto;

import java.time.LocalDateTime;

public class UserBasicInfo {
    private Long id;
    private String fullName;
    private String mobileNumber;
    private String loanType;
    private String loanStatus;
    private LocalDateTime createdAt;

    // Constructor
    public UserBasicInfo(Long id, String fullName, String mobileNumber,
                         String loanType, String loanStatus, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


