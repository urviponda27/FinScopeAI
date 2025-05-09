package com.example.demo.model;

import jakarta.persistence.*;
// import java.util.*;

@Entity
@Table(name = "updates")
public class Updates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creditTier;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String suggestionsJson;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    public Updates() {
    }

    public Updates(Long id, String creditTier, String suggestionsJson, User user) {
        this.id = id;
        this.creditTier = creditTier;
        this.suggestionsJson = suggestionsJson;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditTier() {
        return creditTier;
    }

    public void setCreditTier(String creditTier) {
        this.creditTier = creditTier;
    }

    public String getSuggestionsJson() {
        return suggestionsJson;
    }

    public void setSuggestionsJson(String suggestionsJson) {
        this.suggestionsJson = suggestionsJson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
