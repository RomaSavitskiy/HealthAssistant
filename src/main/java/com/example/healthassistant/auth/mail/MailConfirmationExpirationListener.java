package com.example.healthassistant.auth.mail;

import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MailConfirmationExpirationListener {
    @PrePersist
    public void prePersist(MailConfirmation entity) {
        entity.setExpirationDate(LocalDateTime.now().plus(10, ChronoUnit.MINUTES)); // Срок жизни 1 день
    }
}
