package com.kylewelch.leetcode_progression_tracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kylewelch.leetcode_progression_tracker.dto.ProblemReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.ProblemResDto;
import com.kylewelch.leetcode_progression_tracker.enums.Difficulty;
import com.kylewelch.leetcode_progression_tracker.exception.ResourceNotFoundException;
import com.kylewelch.leetcode_progression_tracker.model.LeetcodeProblem;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProblemService {
    private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);
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

    public List<LeetcodeProblem> getAllLeetcodeProblems() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Referer", "https://leetcode.com");

        String query = """
            query ProblemSetQuestionList($limit: Int, $skip: Int) {
                problemsetQuestionList: questionList(
                    categorySlug: ""
                    filters: {}
                    limit: $limit
                    skip: $skip
                ) {
                    data {
                        questionFrontendId
                        title
                        titleSlug
                        difficulty
                        topicTags {
                            name
                            slug
                        }
                    }
                }
            }
            """;

        // Create the request body as a map for Jackson to serialize
        Map<String, Object> requestBody = Map.of(
            "query", query,
            "variables", Map.of(
                "limit", 2000,
                "skip", 0
            )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
            "https://leetcode.com/graphql",
            HttpMethod.POST,
            request,
            String.class
        );

        logger.info("Received response from LeetCode: {}", response.getBody().substring(0, Math.min(response.getBody().length(), 500)));

        // Parse JSON response and map to LeetcodeProblem objects
        List<LeetcodeProblem> leetcodeProblems = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // Navigate to: data.problemsetQuestionList.data
            JsonNode dataNode = rootNode.path("data")
                                       .path("problemsetQuestionList")
                                       .path("data");

            if (dataNode.isArray() && dataNode.size() > 0) {
                for (JsonNode problemNode : dataNode) {
                    String title = problemNode.path("title").asText();
                    String titleSlug = problemNode.path("titleSlug").asText();
                    String difficultyStr = problemNode.path("difficulty").asText();
                    Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
                    String url = "https://leetcode.com/problems/" + titleSlug + "/";

                    LeetcodeProblem problem = new LeetcodeProblem(title, titleSlug, difficulty, url);
                    leetcodeProblems.add(problem);
                }
                logger.info("Successfully parsed {} LeetCode problems", leetcodeProblems.size());
            } else {
                logger.warn("No problem data found in LeetCode response");
            }
        } catch (Exception e) {
            logger.error("Failed to parse LeetCode response: {}", e.getMessage(), e);
            // Return empty list on parsing error
        }

        return leetcodeProblems;
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

