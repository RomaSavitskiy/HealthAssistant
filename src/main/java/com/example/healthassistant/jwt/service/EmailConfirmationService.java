package com.example.healthassistant.jwt.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import com.example.healthassistant.jwt.repository.EmailConfirmationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
