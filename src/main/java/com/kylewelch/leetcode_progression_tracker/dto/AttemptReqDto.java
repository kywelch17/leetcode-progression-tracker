package com.kylewelch.leetcode_progression_tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AttemptReqDto {
    @Size(max = 1500, message = "Note must not exceed 1500 characters")
    private String note;

    @NotNull(message = "Success status is required")
    private Boolean isSuccessful;

    public AttemptReqDto() {
    }

    public AttemptReqDto(String note, Boolean isSuccessful) {
        this.note = note;
        this.isSuccessful = isSuccessful;
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
}
