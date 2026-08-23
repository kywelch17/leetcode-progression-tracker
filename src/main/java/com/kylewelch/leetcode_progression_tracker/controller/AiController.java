package com.kylewelch.leetcode_progression_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.kylewelch.leetcode_progression_tracker.service.AiService;
import com.kylewelch.leetcode_progression_tracker.dto.AiReqDto;
import com.kylewelch.leetcode_progression_tracker.dto.AiResDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping
    public AiResDto ask(@RequestBody @Valid AiReqDto reqDto) {
        return aiService.ask(reqDto.getNote());
    }
}
