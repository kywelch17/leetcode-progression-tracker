package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.dto.ProblemReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.ProblemResDto;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;

    // Constuctor injection
    public ProblemService(ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }

    // Creating a problem
    public ProblemResDto createProblem(ProblemReqDto reqDto) {
        Problem problem = new Problem();
        problem.setTitle(reqDto.getTitle());
        problem.setDifficulty(reqDto.getDifficulty());
        problem.setNote(reqDto.getNote());
        problem.setUrl(reqDto.getUrl());

        Problem savedProblem = problemRepository.save(problem);

        return mapToDto(savedProblem);
    }

    // Get all problems
    public List<ProblemResDto> getAllProblems() {
        return problemRepository.findAll().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    // Helper to convert Entity to Resp DTO
    private ProblemResDto mapToDto(Problem problem) {
        return modelMapper.map(problem, ProblemResDto.class);
    }
}
