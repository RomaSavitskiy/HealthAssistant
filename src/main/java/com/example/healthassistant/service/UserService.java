package com.example.healthassistant.service;

import com.example.healthassistant.jwt.repository.UserRoleRepository;
import com.example.healthassistant.jwt.service.RandomDigitsService;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.repository.UserRepository;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.UserResponseTo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final EmailService emailService;
    private final RandomDigitsService randomDigitsService;

    public UserResponseTo save(@Valid UserRequestTo requestTo) {
        User entity = mapper.dtoToEntity(requestTo);
        log.info("1");
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(userRoleRepository.findById(1L).stream().collect(Collectors.toSet()));
        entity.setActivationCode(randomDigitsService.generateRandomDigits(6));
        log.info("2");

        if(entity.getUsername() != null) {
            log.info("3");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(entity.getUsername());
            mailMessage.setSubject("Complete Registration!");
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to HealthAssistant, your code: %s",
                    entity.getUsername(),
                    entity.getActivationCode()
            );
            log.info("4");
            mailMessage.setText(message);
            emailService.sendEmail(mailMessage);
            log.info("5");
        }

        log.info("6");

        return mapper.entityToDto(repository.save(entity));
    }
    public UserResponseTo findById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }

    public Iterable<UserResponseTo> findAll() {
        return mapper.entityToDto(repository.findAll());
    }

    public UserResponseTo update(@Valid UserRequestTo requestTo) throws InvocationTargetException, IllegalAccessException {
        User updatedUser = mapper.dtoToEntity(requestTo);
        User oldUser = repository.findById(requestTo.id()).orElseThrow();
        Long id = oldUser.getId();
        BeanUtils.copyProperties(oldUser, updatedUser);
        oldUser.setId(id);
		return mapper.entityToDto(repository.save(oldUser));
    }
    public void deleteById(@Min(0) Long id) {
		repository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByActivateCode(String code) {
        return repository.findByActivationCode(code);
    }
}


