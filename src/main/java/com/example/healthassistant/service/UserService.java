package com.example.healthassistant.service;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.repository.UserRepository;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.UserResponseTo;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseTo save(@Valid UserRequestTo requestTo) {
        User entity = mapper.dtoToEntity(requestTo);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return mapper.entityToDto(repository.save(entity));
    }

    public UserResponseTo getById(@Min(0) Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow();
    }

    public Iterable<UserResponseTo> findAll() {
        return mapper.entityToDto(repository.findAll());
    }

    public UserResponseTo update(@Valid UserRequestTo requestTo) {
        User updatedUser = mapper.dtoToEntity(requestTo);
        User oldUser = repository.findById(requestTo.id()).orElseThrow();
        BeanUtils.copyProperties(updatedUser, oldUser);
		return mapper.entityToDto(updatedUser);
    }
    public void deleteById(@Min(0) Long id) {
		repository.deleteById(id);
    }
}
