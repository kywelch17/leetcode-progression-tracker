package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.dto.AttemptReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.AttemptResDto;
import com.kylewelch.leetcode_progression_tracker.model.Attempt;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.AttemptRepository;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;

    public AttemptService(AttemptRepository attemptRepository, ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.attemptRepository = attemptRepository;
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }

    public AttemptResDto createAttemptForProblem(Long problemId, AttemptReqDto reqDto) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Could not find problem " + problemId));

        Attempt attempt = modelMapper.map(reqDto, Attempt.class);
        attempt.setProblem(problem);

        Attempt savedAttempt = attemptRepository.save(attempt);

        return modelMapper.map(savedAttempt, AttemptResDto.class);
    }

    public List<AttemptResDto> getAttemptsForProblem(Long problemId) {
        return attemptRepository.findByProblemId(problemId)
            .stream()
            .map(attempt -> modelMapper.map(attempt, AttemptResDto.class))
            .toList();
    }

    public Optional<AttemptResDto> getAttempt(Long attemptId) {
        return attemptRepository.findById(attemptId)
            .map(attempt -> modelMapper.map(attempt, AttemptResDto.class));
    }
}
