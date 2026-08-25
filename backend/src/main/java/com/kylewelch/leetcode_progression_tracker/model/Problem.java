package com.kylewelch.leetcode_progression_tracker.model;

import java.time.LocalDateTime;

import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;

import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(length = 1500)
    private String note;

    private String title;
    private String url;
    private LocalDateTime createdAt = LocalDateTime.now();

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}