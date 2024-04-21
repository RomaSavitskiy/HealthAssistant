package com.example.healthassistant.jwt.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import com.example.healthassistant.jwt.repository.EmailConfirmationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {
    private final EmailConfirmationRepository emailConfirmationRepository;

    public EmailConfirmation findLastCodeByLogin(String login) {
        return emailConfirmationRepository.findTopByLoginOrderByIdDesc(login).orElseThrow(
                () -> new NotFoundException(404L, "Email is not found")
        );
    }

    public EmailConfirmation save(EmailConfirmation emailConfirmation) {
        return emailConfirmationRepository.save(emailConfirmation);
    }

    @Scheduled(cron = "0 */10 * * * ?")// Выполняется каждые 10 минут
    @Transactional
    public void cleanExpiredEntities() {
        LocalDateTime now = LocalDateTime.now();
        emailConfirmationRepository.deleteByExpirationDateBefore(now);
    }
}
