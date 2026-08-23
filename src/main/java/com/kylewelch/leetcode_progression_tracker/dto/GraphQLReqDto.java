package com.kylewelch.leetcode_progression_tracker.dto;

import java.util.Map;

public record GraphQLReqDto(
    String query,
    Map<String, Object> variables
){};