package com.kylewelch.leetcode_progression_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kylewelch.leetcode_progression_tracker.model.Attempt;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByProblemId(Long problemId);
    Optional<Attempt> findById(Long attemptId);
}