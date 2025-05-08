package com.example.demo.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class CombinedUserBankDTO {

    // User fields
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits.")
    private String mobileNumber;

    @NotBlank
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN number format.")
    private String panNumber;

    @NotBlank
    @Size(min = 5)
    private String consentId;

    @NotBlank
    @Pattern(regexp = "PERSONAL|HOME|EDUCATION|BUSINESS", message = "Loan type must be PERSONAL, HOME, EDUCATION or BUSINESS")
    private String loanType;

    // BankStatement fields (1:1)
    @NotBlank
    @Size(min = 8, max = 20)
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code.")
    private String ifscCode;


    @NotNull(message = "PDF file is required.")
    private MultipartFile pdfFile;

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public String getConsentId() { return consentId; }
    public void setConsentId(String consentId) { this.consentId = consentId; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }



    public MultipartFile getPdfFile() { return pdfFile; }
    public void setPdfFile(MultipartFile pdfFile) { this.pdfFile = pdfFile; }
}
