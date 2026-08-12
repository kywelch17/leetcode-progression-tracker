package com.kylewelch.leetcode_progression_tracker.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.kylewelch.leetcode_progression_tracker.dto.AttemptReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.AttemptResDto;
import com.kylewelch.leetcode_progression_tracker.service.AttemptService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/problems")
public class AttemptController {
    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/{problemId}/attempts")
    public AttemptResDto addAttempt(@PathVariable Long problemId, @RequestBody AttemptReqDto reqDto) {
        return attemptService.createAttemptForProblem(problemId, reqDto);
    }

    @GetMapping("/{problemId}/attempts")
    public List<AttemptResDto> getAttempts(@PathVariable Long problemId) {
        return attemptService.getAttemptsForProblem(problemId);
    }

    @GetMapping("/attempts/{attemptId}")
    public Optional<AttemptResDto> getAttempt(@PathVariable Long attemptId) {
        return attemptService.getAttempt(attemptId);
    }
    
    
}
