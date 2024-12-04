package com.example.demo.controller;

import com.example.demo.model.GameSession;
import com.example.demo.service.AttemptService;
import com.example.demo.service.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GameSessionController {
    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private AttemptService attemptService;

    @PostMapping("/game/create")
    public Map<String, Object> createGameSession(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            GameSession gameSession = gameSessionService.createGameSession(request.get("number"), request.get("gameRules"), request.get("session"));
            response.put("game-session", gameSession.getSessionId());
            response.put("status", "OK");
            response.put("errorMessage", null);
        } catch (IllegalArgumentException e) {
            response.put("status", "FAIL");
            response.put("errorMessage", e.getMessage());
        }
        return response;
    }

    @GetMapping("/game/{sessionId}/status")
    public Map<String, Object> getGameStatus(@PathVariable String sessionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            GameSession gameSession = gameSessionService.getGameSession(sessionId);
            response.put("status", gameSession.getStatus());
            response.put("attempts", attemptService.getAttemptsAsMap(sessionId));
        } catch (IllegalArgumentException e) {
            response.put("status", "FAIL");
            response.put("errorMessage", e.getMessage());
        }
        return response;
    }
}
