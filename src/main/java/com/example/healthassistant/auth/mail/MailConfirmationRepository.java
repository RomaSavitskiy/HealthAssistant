package com.example.healthassistant.auth.mail;

import com.example.healthassistant.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MailConfirmationRepository extends JpaRepository<MailConfirmation, Long> {
    Optional<MailConfirmation> findTopByLoginOrderByIdDesc(User user);
    void deleteByExpirationDateBefore(LocalDateTime date);
}
