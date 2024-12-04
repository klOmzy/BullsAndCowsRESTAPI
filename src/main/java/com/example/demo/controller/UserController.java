package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = userService.registerUser(request.get("login"), request.get("password"));
            response.put("status", "OK");
            response.put("errorMessage", null);
        } catch (IllegalArgumentException e) {
            response.put("status", "FAIL");
            response.put("errorMessage", e.getMessage());
        }
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String sessionId = userService.loginUser(request.get("login"), request.get("password"));
            response.put("session", sessionId);
            response.put("status", "OK");
            response.put("errorMessage", null);
        } catch (IllegalArgumentException e) {
            response.put("status", "FAIL");
            response.put("errorMessage", e.getMessage());
        }
        return response;
    }
}
