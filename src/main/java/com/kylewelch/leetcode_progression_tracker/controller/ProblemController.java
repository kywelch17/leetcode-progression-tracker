package com.kylewelch.leetcode_progression_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.kylewelch.leetcode_progression_tracker.dto.ProblemReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.ProblemResDto;
import com.kylewelch.leetcode_progression_tracker.service.ProblemService;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    private final ProblemService problemService;

    // Constructor Injection
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping
    public ProblemResDto createProblem(@RequestBody @Valid ProblemReqDto reqDto) {
        return problemService.createProblem(reqDto);
    }

    @GetMapping
    public List<ProblemResDto> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/{id}")
    public ProblemResDto getProblemById(@PathVariable Long id) {
        return problemService.getProblemById(id);
    }
}

