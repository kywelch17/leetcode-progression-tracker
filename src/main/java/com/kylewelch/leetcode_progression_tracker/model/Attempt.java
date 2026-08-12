package com.kylewelch.leetcode_progression_tracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "attempts") 
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long id;

    @Column(length = 1500)
    private String note;

    private Boolean isSuccessful;
    private LocalDateTime attemptedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public Attempt() {

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

    public Boolean getisSuccessful() {
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

    public Problem getProblem() {
        return problem;
    }
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
