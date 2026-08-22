package com.kylewelch.leetcode_progression_tracker.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import com.kylewelch.leetcode_progression_tracker.model.Problem;
import com.kylewelch.leetcode_progression_tracker.repository.ProblemRepository;

@Service
public class AiService {
    private final ChatClient chatClient;
    private final ProblemRepository problemRepository;
    private final ChatMemory chatMemory;

    public AiService(ChatClient.Builder builder, ProblemRepository problemRepository, ChatMemory chatMemory) {
        this.chatClient = builder.build();
        this.problemRepository = problemRepository;
        this.chatMemory = chatMemory;
    }

    public String ask(String prompt) {
        List<String> problemTitles = problemRepository.findAll().stream()
            .map(Problem::getTitle)
            .toList();
        String system = """
                You extract Leetcode practice attempts from a user's note.
                Only use problem titles from this exact list - never invent problems.

                %s

                Rules:
                    - If the user implies multiple attempts, emit one entry per try.
                    - Pick the closest match from the list I provided you if the wording isn't exact match.
                    - Never fabricate a title.
                    - isSuccessful reflects that specific attempt.
                """.formatted(String.join("\n", problemTitles));


        return chatClient.prompt()
            .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "abc-123"))
            .system(system)
            .user(prompt)
            .call()
            .content();
    }
}
