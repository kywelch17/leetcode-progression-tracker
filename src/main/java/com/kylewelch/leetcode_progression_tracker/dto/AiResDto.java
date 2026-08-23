package com.kylewelch.leetcode_progression_tracker.dto;

public class AiResDto {
    private AttemptResDto createdAttempt;
    private String unknownProblem;

    public AiResDto() {

    }

    public AiResDto(AttemptResDto createdAttempt) {
        this.createdAttempt = createdAttempt;
        this.unknownProblem = null;
    }

    public AiResDto(String unknownProblem) {
        this.createdAttempt = null;
        this.unknownProblem = unknownProblem;
    }

    public AttemptResDto getCreatedAttempt() {
        return createdAttempt;
    }

    public void setCreatedAttempt(AttemptResDto createdAttempt) {
        this.createdAttempt = createdAttempt;
    }

    public String getUnknownProblem() {
        return unknownProblem;
    }

    public void setUnknownProblem(String unknownProblem) {
        this.unknownProblem = unknownProblem;
    }
}
