package com.kylewelch.leetcode_progression_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kylewelch.leetcode_progression_tracker.model.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
}
