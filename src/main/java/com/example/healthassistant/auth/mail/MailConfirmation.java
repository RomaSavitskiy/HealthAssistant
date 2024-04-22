package com.example.healthassistant.auth.mail;

import com.example.healthassistant.auth.mail.MailConfirmationExpirationListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@EntityListeners(MailConfirmationExpirationListener.class)
public class MailConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String login;

    private LocalDateTime expirationDate;
}
