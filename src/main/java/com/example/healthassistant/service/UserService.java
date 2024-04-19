package com.example.healthassistant.service;

import com.example.healthassistant.jwt.repository.UserRoleRepository;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.repository.UserRepository;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.UserResponseTo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserResponseTo save(@Valid UserRequestTo requestTo) {
        User entity = mapper.dtoToEntity(requestTo);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRoles(userRoleRepository.findById(1L).stream().collect(Collectors.toSet()));
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
}
