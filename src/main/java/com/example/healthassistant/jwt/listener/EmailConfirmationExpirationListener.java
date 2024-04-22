package com.example.healthassistant.jwt.listener;

import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmailConfirmationExpirationListener {
    @PrePersist
    public void prePersist(EmailConfirmation entity) {
        entity.setExpirationDate(LocalDateTime.now().plus(10, ChronoUnit.MINUTES)); // Срок жизни 1 день
    }
}
