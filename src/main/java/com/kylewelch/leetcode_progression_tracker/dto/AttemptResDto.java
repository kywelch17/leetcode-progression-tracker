package com.kylewelch.leetcode_progression_tracker.dto;

import java.time.LocalDateTime;

public class AttemptResDto {
    private Long id;
    private String note;
    private Boolean isSuccessful;
    private LocalDateTime attemptedAt;

    public AttemptResDto() {
    }

    public AttemptResDto(Long id, String note, Boolean isSuccessful, LocalDateTime attemptedAt) {
        this.id = id;
        this.note = note;
        this.isSuccessful = isSuccessful;
        this.attemptedAt = attemptedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(LocalDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }
}
