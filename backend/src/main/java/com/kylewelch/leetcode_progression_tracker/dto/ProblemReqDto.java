package com.kylewelch.leetcode_progression_tracker.dto;

import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ProblemReqDto {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @Size(max = 1500, message = "Note must not exceed 1500 characters")
    private String note;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Please provide a valid URL")
    @Size(max = 500, message = "URL must not exceed 500 characters")
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
