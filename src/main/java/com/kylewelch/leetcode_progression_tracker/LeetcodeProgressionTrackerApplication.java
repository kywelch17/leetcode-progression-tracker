package com.kylewelch.leetcode_progression_tracker;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeetcodeProgressionTrackerApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		// SpringApplication.run(LeetcodeProgressionTrackerApplication.class, args);
		SpringApplication app = new SpringApplication(LeetcodeProgressionTrackerApplication.class);
		app.run(args);
	}

}
