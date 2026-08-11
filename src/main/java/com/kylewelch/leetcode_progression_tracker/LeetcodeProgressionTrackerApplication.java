package com.kylewelch.leetcode_progression_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LeetcodeProgressionTrackerApplication {

	@RequestMapping("/")
	private String home() {
		return "We are making a comeback, baby!";
	}

	public static void main(String[] args) {
		SpringApplication.run(LeetcodeProgressionTrackerApplication.class, args);
	}

}
