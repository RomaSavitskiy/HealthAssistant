package com.example.healthassistant.auth.mail;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailConfirmationService {
    private final MailConfirmationRepository mailConfirmationRepository;
    private final UserServiceImpl userServiceImpl;

    public MailConfirmation findLastCodeByLogin(String login) {
        return mailConfirmationRepository.findTopByLoginOrderByIdDesc(login)
                .orElseThrow(() -> new NotFoundException(404L, "Email is not found")
        );
    }

    public MailConfirmation save(MailConfirmation mailConfirmation) {
        return mailConfirmationRepository.save(mailConfirmation);
    }

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional
    public void cleanExpiredEntities() {
        LocalDateTime now = LocalDateTime.now();
        mailConfirmationRepository.deleteByExpirationDateBefore(now);
    }
}
