package com.kylewelch.leetcode_progression_tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProblemTest {

    @Test
    void gettersAndSettersWork() {
        Problem problem = new Problem();

        problem.setId(42L);
        problem.setTitle("Two Sum");

        assertEquals(42L, problem.getId());
        assertEquals("Two Sum", problem.getTitle());
    }
}
