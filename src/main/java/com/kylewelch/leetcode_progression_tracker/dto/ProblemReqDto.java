package com.kylewelch.leetcode_progression_tracker.dto;

import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;

public class ProblemReqDto {
    private String title;
    private Difficulty difficulty;
    private String note;
    private String url;

    public ProblemReqDto() {
        
    }

    public ProblemReqDto(String title, Difficulty difficulty, String note, String url) {
        this.title = title;
        this.difficulty = difficulty;
        this.note = note;
        this.url = url;
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
}
