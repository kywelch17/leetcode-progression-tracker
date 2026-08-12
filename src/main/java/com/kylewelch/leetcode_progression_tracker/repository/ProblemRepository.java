package com.kylewelch.leetcode_progression_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kylewelch.leetcode_progression_tracker.model.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
}
