package com.kylewelch.leetcode_progression_tracker.dto;

public class AttemptReqDto {
    private String note;
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
