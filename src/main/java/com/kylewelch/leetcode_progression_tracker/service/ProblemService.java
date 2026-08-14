package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.dto.ProblemReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.ProblemResDto;
import com.kylewelch.leetcode_progression_tracker.helper.MapperUtil;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final MapperUtil mapper;

    // Constuctor injection
    public ProblemService(ProblemRepository problemRepository, MapperUtil mapper) {
        this.problemRepository = problemRepository;
        this.mapper = mapper;
    }

    // Creating a problem
    public ProblemResDto createProblem(ProblemReqDto reqDto) {
        Problem problem = new Problem();
        problem.setTitle(reqDto.getTitle());
        problem.setDifficulty(reqDto.getDifficulty());
        problem.setNote(reqDto.getNote());
        problem.setUrl(reqDto.getUrl());

        Problem savedProblem = problemRepository.save(problem);

        return mapper.mapToDto(savedProblem, ProblemResDto.class);
    }

    // Get all problems
    public List<ProblemResDto> getAllProblems() {
        return problemRepository.findAll().stream()
            .map(problem -> mapper.mapToDto(problem, ProblemResDto.class))
            .collect(Collectors.toList());
    }
}
