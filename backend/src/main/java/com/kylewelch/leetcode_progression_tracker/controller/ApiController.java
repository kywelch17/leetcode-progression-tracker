package com.kylewelch.leetcode_progression_tracker.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping
    public Map<String, String> apiRoot() {
        return Map.of(
            "status", "UP",
            "msg", "Welcome to the Leetcode Progression Tracker!"
        );
    }  
}
