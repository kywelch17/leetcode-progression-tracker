package com.kylewelch.leetcode_progression_tracker;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeetcodeProgressionTrackerApplication {

	@Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
            .maxMessages(25) 
            .build();
    }

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LeetcodeProgressionTrackerApplication.class);
		app.run(args);
	}

}

