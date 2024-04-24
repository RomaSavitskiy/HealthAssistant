package com.example.healthassistant.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface UserService {
    UserResponseTo save(@Valid UserRequestTo requestTo);

    UserResponseTo findById(@Min(0) Long id);

    Iterable<UserResponseTo> findAll();

    UserResponseTo update(@Valid UserRequestTo requestTo)
            throws InvocationTargetException, IllegalAccessException;

    void deleteById(@Min(0) Long id);

    Optional<User> findByUsername(String username);
}
