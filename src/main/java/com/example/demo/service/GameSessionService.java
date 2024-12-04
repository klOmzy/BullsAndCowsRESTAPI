package com.example.demo.service;

import com.example.demo.model.GameSession;
import com.example.demo.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class GameSessionService {
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private UserService userService;

    public GameSession createGameSession(String number, String gameRules, String sessionId) {
        boolean repeat = "repeat".equalsIgnoreCase(gameRules);

        if (!repeat && hasRepeatingDigits(number)) {
            throw new IllegalArgumentException("Повторяются цифры");
        }

        if (!userService.validateSession(sessionId)) {
            throw new IllegalArgumentException("Неверная сессия");
        }

        String gameSessionId = UUID.randomUUID().toString();
        GameSession gameSession = new GameSession();
        gameSession.setSessionId(gameSessionId);
        gameSession.setNumber(number);
        gameSession.setGameRules(gameRules);
        gameSession.setStatus("IN_PROGRESS");
        return gameSessionRepository.save(gameSession);
    }

    private boolean hasRepeatingDigits(String number) {
        Set<Character> digits = new HashSet<>();
        for (char digit : number.toCharArray()) {
            if (!digits.add(digit)) {
                return true;
            }
        }
        return false;
    }

    public GameSession getGameSession(String sessionId) {
        return gameSessionRepository.findBySessionId(sessionId);
    }
}
