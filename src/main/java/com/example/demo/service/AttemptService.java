package com.example.demo.service;

import com.example.demo.model.Attempt;
import com.example.demo.model.GameSession;
import com.example.demo.repository.AttemptRepository;
import com.example.demo.repository.GameSessionRepository;
import com.game.bullsandcows.result.GuessAndResultCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttemptService {
    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public Attempt makeAttempt(String sessionId, String number) {
        GameSession gameSession = gameSessionRepository.findBySessionId(sessionId);
        if (gameSession == null) {
            throw new IllegalArgumentException("Игра не найдена");
        }

        if ("COMPLETED".equals(gameSession.getStatus())) {
            throw new IllegalArgumentException("Игра уже завершена");
        }

        if (number.length() < gameSession.getNumber().length() || number.length() > gameSession.getNumber().length()) {
            throw new IllegalArgumentException("Количество цифр должно совпадать с секретным числом");
        }
        GuessAndResultCheck guessAndResultCheck = new GuessAndResultCheck();
        int[] result = guessAndResultCheck.checkResult(gameSession.getNumber(), number);
        int bulls = result[0];
        int cows = result[1];

        Attempt attempt = new Attempt();
        attempt.setAssumption(number);
        attempt.setBulls(bulls);
        attempt.setCows(cows);
        attempt.setGameSession(gameSession);

        attemptRepository.save(attempt);

        if (bulls == gameSession.getNumber().length()) {
            gameSession.setStatus("COMPLETED");
            gameSessionRepository.save(gameSession);
        }

        return attempt;
    }

    public List<Attempt> getAttempts(String sessionId) {
        GameSession gameSession = gameSessionRepository.findBySessionId(sessionId);
        if (gameSession == null) {
            throw new IllegalArgumentException("Игра не найдена");
        }
        return attemptRepository.findByGameSession(gameSession);
    }

    public List<Map<String, Object>> getAttemptsAsMap(String sessionId) {
        List<Attempt> attempts = getAttempts(sessionId);
        return attempts.stream().map(attempt -> {
            Map<String, Object> attemptMap = new HashMap<>();
            attemptMap.put("assumption", attempt.getAssumption());
            attemptMap.put("bulls", attempt.getBulls());
            attemptMap.put("cows", attempt.getCows());
            return attemptMap;
        }).collect(Collectors.toList());
    }
}
