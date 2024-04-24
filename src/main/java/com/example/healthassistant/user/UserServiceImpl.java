package com.example.healthassistant.user;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.auth.role.UserRoleRepository;
import com.example.healthassistant.auth.utils.RandomDigitsService;
import com.example.healthassistant.auth.mail.MailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final MailService mailService;
    private final RandomDigitsService randomDigitsService;

    public UserResponseTo save(@Valid UserRequestTo requestTo) {
        User entity = mapper.dtoToEntity(requestTo);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(userRoleRepository.findById(1L).stream().collect(Collectors.toSet()));

        return mapper.entityToDto(repository.save(entity));
    }
    public UserResponseTo findById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException(404L, "User not found"));
    }

    public Iterable<UserResponseTo> findAll() {
        return mapper.entityToDto(repository.findAll());
    }

    public UserResponseTo update(@Valid UserRequestTo requestTo)
            throws InvocationTargetException, IllegalAccessException {
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

    /*public Optional<User> findByActivateCode(String code) {
        return repository.findByActivationCode(code);
    }*/
}


