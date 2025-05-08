package com.example.demo.dto;

public class LoginResponse {

    private String message;

    // Constructors
    public LoginResponse() {
    }

    public LoginResponse(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
