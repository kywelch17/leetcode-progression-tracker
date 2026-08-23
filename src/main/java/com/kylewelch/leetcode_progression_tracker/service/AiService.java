package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylewelch.leetcode_progression_tracker.model.Attempt;
import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;
import com.kylewelch.leetcode_progression_tracker.repository.AttemptRepository;
import com.kylewelch.leetcode_progression_tracker.dto.AiResDto;
import com.kylewelch.leetcode_progression_tracker.dto.AttemptResDto;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AiService {
    private static final Logger logger = LoggerFactory.getLogger(AiService.class);
    private final ChatClient chatClient;
    private final ProblemRepository problemRepository;
    private final AttemptRepository attemptRepository;
    private final ChatMemory chatMemory;
    private final ObjectMapper objectMapper;

    public AiService(ChatClient.Builder builder, ProblemRepository problemRepository, 
        ChatMemory chatMemory, ObjectMapper objectMapper, AttemptRepository attemptRepository) {
        this.chatClient = builder.build();
        this.problemRepository = problemRepository;
        this.attemptRepository = attemptRepository;
        this.chatMemory = chatMemory;
        this.objectMapper = objectMapper;
    }

    public AiResDto ask(String note) {
        List<String> problemTitles = problemRepository.findAll().stream()
            .map(Problem::getTitle)
            .toList();

        String system = String.format("""
                You are a Leetcode progression tracker. Extract the attempt from the users prompt.

                AVAILABLE PROBLEMS (USE THESE TITLES EXACTLY CASE INSENSITIVE - IF SMALL TINY TYPO, FIND CLOSEST ONE IF POSSIBLE)
                %s

                For each attempt, respond with the following JSON format:
                {
                    "title": "Any title from above",
                    "successful": true/false
                }

                Rules:
                1) YOU MUST ONLY RETURN A PROBLEM TITLE IF THE USER'S NOTE CONTAINS THE EXACT TEXT OF A LEETCODE PROBLEM TITLE FROM THE LIST ABOVE
                2) DO NOT RETURN A PROBLEM TITLE BASED ON ASSOCIATIONS, SIMILARITY, INFERENCES, OR CONTEXT CLUES
                3) DO NOT RETURN A PROBLEM TITLE FOR CASUAL CONVERSATION, JOKES, SLANG, OR UNRELATED STATEMENTS
                4) TO RETURN A PROBLEM TITLE, THE USER'S NOTE MUST CONTAIN AT LEAST ONE OF THE EXACT PROBLEM TITLES LISTED ABOVE (CASE INSENSITIVE MATCH)
                5) IF THE USER'S NOTE DOES NOT CONTAIN AN EXACT PROBLEM TITLE FROM THE LIST, RETURN { "title": "", "successful": false }
                6) IF THERE IS A TYPO IN A PROBLEM TITLE REFERENCE, YOU MAY ATTEMPT TO FIND THE CLOSEST MATCH FROM THE LIST ABOVE
                7) SET successful=true ONLY IF THE USER EXPLICITLY STATES THEY COMPLETED/SOLVED THE PROBLEM
                8) SET successful=false IN ALL OTHER CASES (UNCLEAR, NOT COMPLETED, OR NO CLEAR PROBLEM REFERENCE)
                9) WHEN IN DOUBT ABOUT WHETHER TO RETURN A PROBLEM TITLE, RETURN EMPTY TITLE
                10) RESPOND ONLY WITH THE JSON OBJECT - NO ADDITIONAL TEXT, EXPLANATION, OR FORMATTING
                """, String.join("\n", problemTitles));

        


        String response;
        
        try {
            response = chatClient.prompt()
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "analyze-" + System.currentTimeMillis()))
                .system(system)
                .user(note)
                .call()
                .content();
        } catch (Exception e) {
            logger.error("There was a problem: ", e);
            throw e;
        }

        logger.info("AI raw response: {}", response);

        // Parse the JSON response - be lenient, extract title and successful fields
        String title = null;
        Boolean successful = null;
        try {
            JsonNode root = objectMapper.readTree(response);
            if (root.has("title")) {
                title = root.get("title").asString(null);
            }
            if (root.has("successful")) {
                successful = root.get("successful").booleanValue();
            }
        } catch (Exception e) {
            logger.error("Could not parse AI response as JSON: {}", response, e);
            return new AiResDto("Could not understand. Please try again.");
        }

        // If we couldn't extract title or successful, treat as failure to parse
        if (title == null && successful == null) {
            logger.error("AI response missing both title and successful fields: {}", response);
            return new AiResDto("Could not understand. Please try again.");
        }

        title = title.trim();
        if (title.isEmpty()) {
            return new AiResDto("I could not get the problem of your Leetcode problem. Please try again.");
        }

        Optional<Problem> problemOpt = problemRepository.findByTitleIgnoreCase(title);
        if (!problemOpt.isPresent()) {
            return new AiResDto(String.format("I am not familiar with %s. Please try again.", String.join("\n", title)));
        }

        Problem problem = problemOpt.get();

        // Create attempt
        Attempt attempt = new Attempt();
        attempt.setNote(note);
        attempt.setIsSuccessful(successful);
        attempt.setProblem(problem);

        Attempt savedAttempt = attemptRepository.save(attempt);
        AttemptResDto attemptDto = new AttemptResDto(
            savedAttempt.getId(),
            savedAttempt.getNote(),
            savedAttempt.getIsSuccessful(),
            savedAttempt.getAttemptedAt()
        );

        return new AiResDto(attemptDto);
    }
}
