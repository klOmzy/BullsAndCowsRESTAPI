package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private Map<String, String> userSessions = new HashMap<>();

    public AppUser registerUser(String login, String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Пароль не может быть меньше 6 символов");
        }
        AppUser user = new AppUser();
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public String loginUser(String login, String password) {
        AppUser user = userRepository.findByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный пароль");
        }
        String sessionId = UUID.randomUUID().toString();
        userSessions.put(sessionId, login);
        return sessionId;
    }

    public boolean validateSession(String sessionId) {
        return userSessions.containsKey(sessionId);
    }
}
