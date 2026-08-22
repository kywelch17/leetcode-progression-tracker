package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.dto.ProblemReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.ProblemResDto;
import com.kylewelch.leetcode_progression_tracker.exception.ResourceNotFoundException;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public ProblemResDto createProblem(ProblemReqDto reqDto) {
        Problem problem = new Problem();
        problem.setTitle(reqDto.getTitle());
        problem.setDifficulty(reqDto.getDifficulty());
        problem.setNote(reqDto.getNote());
        problem.setUrl(reqDto.getUrl());

        Problem savedProblem = problemRepository.save(problem);
        return toDto(savedProblem);
    }

    public List<ProblemResDto> getAllProblems() {
        return problemRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public ProblemResDto getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));
        return toDto(problem);
    }

    private ProblemResDto toDto(Problem problem) {
        return new ProblemResDto(
            problem.getId(),
            problem.getTitle(),
            problem.getDifficulty(),
            problem.getNote(),
            problem.getUrl(),
            problem.getCreatedAt()
        );
    }
}

