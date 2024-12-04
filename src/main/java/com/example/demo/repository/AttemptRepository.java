package com.example.demo.repository;

import com.example.demo.model.Attempt;
import com.example.demo.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByGameSession(GameSession gameSession);
}
