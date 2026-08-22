package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.dto.AttemptReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.AttemptResDto;
import com.kylewelch.leetcode_progression_tracker.exception.ResourceNotFoundException;
import com.kylewelch.leetcode_progression_tracker.model.Attempt;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.AttemptRepository;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final ProblemRepository problemRepository;

    public AttemptService(AttemptRepository attemptRepository, ProblemRepository problemRepository) {
        this.attemptRepository = attemptRepository;
        this.problemRepository = problemRepository;
    }

    public AttemptResDto createAttemptForProblem(Long problemId, AttemptReqDto reqDto) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new ResourceNotFoundException("Could not find problem with id: " + problemId));

        Attempt attempt = new Attempt();
        attempt.setNote(reqDto.getNote());
        attempt.setIsSuccessful(reqDto.getIsSuccessful());
        attempt.setProblem(problem);

        Attempt savedAttempt = attemptRepository.save(attempt);
        return toDto(savedAttempt);
    }

    public List<AttemptResDto> getAttemptsForProblem(Long problemId) {
        if (!problemRepository.existsById(problemId)) {
            throw new ResourceNotFoundException("Could not find problem with id: " + problemId);
        }
        return attemptRepository.findByProblemId(problemId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public AttemptResDto getAttempt(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
            .orElseThrow(() -> new ResourceNotFoundException("Could not find attempt with id: " + attemptId));
        return toDto(attempt);
    }

    private AttemptResDto toDto(Attempt attempt) {
        return new AttemptResDto(
            attempt.getId(),
            attempt.getNote(),
            attempt.getIsSuccessful(),
            attempt.getAttemptedAt()
        );
    }
}


