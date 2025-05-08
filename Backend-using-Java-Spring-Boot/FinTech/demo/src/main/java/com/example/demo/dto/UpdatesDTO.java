package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public class UpdatesDTO {

    @NotBlank(message = "Credit tier must not be blank")
    private String creditTier;

    private Map<String, Integer> suggestions; // nullable and optional

    public UpdatesDTO() {}

    public UpdatesDTO(String creditTier, Map<String, Integer> suggestions) {
        this.creditTier = creditTier;
        this.suggestions = suggestions;
    }

    public String getCreditTier() {
        return creditTier;
    }

    public void setCreditTier(String creditTier) {
        this.creditTier = creditTier;
    }

    public Map<String, Integer> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(Map<String, Integer> suggestions) {
        this.suggestions = suggestions;
    }
}
