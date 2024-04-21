package com.example.healthassistant.jwt.repository;

import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {
    Optional<EmailConfirmation> findTopByLoginOrderByIdDesc(String login);
    void deleteByExpirationDateBefore(LocalDateTime date);
}
