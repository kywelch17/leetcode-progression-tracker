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
        AiResponse aiResponse;

        String system = String.format("""
                You are a Leetcode progression tracker. Extract the attempt from the users prompt.

                AVAILABLE PROBLEMS (USE THESE TITLES EXACTLY CASE INSENSITIVE - IF TYPO, FIND CLOSEST ONE IF POSSIBLE)
                %s

                For each attempt, respond with the following JSON format:
                {
                    "title": "Any title from above",
                    "successful": true/false
                }

                Rules:
                1) NEVER infer or associate a Leetcode problem title from informal speech, slang, jokes, or unrelated conversations
                2) ONLY return a problem title if the user explicitly mentions a Leetcode problem BY ITS EXACT NAME from the list above
                3) Examples of what NOT to match:
                   - Casual conversation without Leetcode problem names → should return empty title
                4) If the user's note does NOT contain an EXACT Leetcode problem name from the list above, return { "title": "", "successful": false }
                5) If typo in problem reference, find closest one from the list above (if possible) - but ONLY if clearly attempting to reference a Leetcode problem
                6) Set successful=true ONLY if they explicitly said they were able to complete it
                7) Set successful=false if they explicitly said they weren't able to complete it, if unclear, or if no clear problem reference
                8) WHEN IN DOUBT, RETURN EMPTY TITLE - it's better to miss a real attempt than to falsely create one
                9) Respond ONLY with the JSON object - no additional text, explanation, or formatting.
                """, String.join("\n", problemTitles));

        


        String response = chatClient.prompt()
            .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "analyze-" + System.currentTimeMillis()))
            .system(system)
            .user(note)
            .call()
            .content();

        // Parse the JSON response
        try {
            aiResponse = objectMapper.readValue(response, AiResponse.class);
        } catch (Exception e) {
            logger.error("Could not parse AI response: {}", response, e);
            return new AiResDto("Could not understand. Please try again.");
        }

        String title = aiResponse.getTitle().trim();
        if (title.isEmpty()) {
            return new AiResDto("I could not get the problem of your Leetcode problem. Please try again.");
        }

        Optional<Problem> problemOpt = problemRepository.findByTitleIgnoreCase(title);
        if (!problemOpt.isPresent()) {
            return new AiResDto(String.format("I am not familiar with %s. Please try again.", String.join("\n", title)));
        }

        Problem problem = problemOpt.get();
        Boolean successful = aiResponse.getSuccessful();

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

    private static class AiResponse {
        private String title;
        private Boolean successful;
        
        public AiResponse() {

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Boolean getSuccessful() {
            return successful;
        }

        public void setSuccessful(Boolean successful) {
            this.successful = successful;
        }
    }
}
