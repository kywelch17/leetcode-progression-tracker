package com.kylewelch.leetcode_progression_tracker.dto;

import java.time.LocalDateTime;

import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;

public class ProblemResDto {
    private Long id;
    private String title;
    private Difficulty difficulty;
    private String note;
    private String url;
    private LocalDateTime createdAt;

    public ProblemResDto() {

    }

    public ProblemResDto(Long id, String title, Difficulty difficulty, String note, String url, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.note = note;
        this.url = url;
        this.createdAt = createdAt;
    }

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
