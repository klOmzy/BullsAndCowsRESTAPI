package com.example.demo.controller;

import com.example.demo.model.Attempt;
import com.example.demo.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AttemptController {
    @Autowired
    private AttemptService attemptService;

    @PostMapping("/game/{sessionId}/attempt")
    public Map<String, Object> makeAttempt(@PathVariable String sessionId, @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Attempt attempt = attemptService.makeAttempt(sessionId, request.get("number"));
            response.put("bulls", attempt.getBulls());
            response.put("cows", attempt.getCows());
            response.put("status", "OK");
            response.put("errorMessage", null);
        } catch (IllegalArgumentException e) {
            response.put("status", "FAIL");
            response.put("errorMessage", e.getMessage());
        }
        return response;
    }
}
