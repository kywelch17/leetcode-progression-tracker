package com.kylewelch.leetcode_progression_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;

@Entity
public class LeetcodeProblem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String titleSlug;
    private Difficulty difficulty;
    private String url;
    
    // Constructors
    public LeetcodeProblem() {
    }
    
    public LeetcodeProblem(String title, String titleSlug, Difficulty difficulty, String url) {
        this.title = title;
        this.titleSlug = titleSlug;
        this.difficulty = difficulty;
        this.url = url;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitleSlug() {
        return titleSlug;
    }
    
    public void setTitleSlug(String titleSlug) {
        this.titleSlug = titleSlug;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
