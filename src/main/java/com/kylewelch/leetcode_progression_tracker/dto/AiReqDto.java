package com.kylewelch.leetcode_progression_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class AiReqDto {
    @NotBlank(message = "A note is required")
    private String note;

    public AiReqDto() { 
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
