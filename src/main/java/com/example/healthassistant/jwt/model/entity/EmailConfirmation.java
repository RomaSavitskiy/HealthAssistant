package com.example.healthassistant.jwt.model.entity;

import com.example.healthassistant.jwt.listener.EmailConfirmationExpirationListener;
import com.example.healthassistant.model.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@EntityListeners(EmailConfirmationExpirationListener.class)
public class EmailConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String login;

    private LocalDateTime expirationDate;
}
