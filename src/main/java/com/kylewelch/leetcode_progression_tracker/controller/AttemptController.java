package com.kylewelch.leetcode_progression_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import com.kylewelch.leetcode_progression_tracker.dto.AttemptReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.AttemptResDto;
import com.kylewelch.leetcode_progression_tracker.service.AttemptService;


@RestController
@RequestMapping("/api/problems")
public class AttemptController {
    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/{problemId}/attempts")
    public AttemptResDto addAttempt(@PathVariable Long problemId, @RequestBody @Valid AttemptReqDto reqDto) {
        return attemptService.createAttemptForProblem(problemId, reqDto);
    }

    @GetMapping("/{problemId}/attempts")
    public List<AttemptResDto> getAttempts(@PathVariable Long problemId) {
        return attemptService.getAttemptsForProblem(problemId);
    }

    @GetMapping("/attempts/{attemptId}")
    public AttemptResDto getAttempt(@PathVariable Long attemptId) {
        return attemptService.getAttempt(attemptId);
    }
    
    
}
