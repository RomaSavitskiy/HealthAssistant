package com.example.healthassistant.jwt.model.service;

import com.example.healthassistant.exceptions.UserAlreadyExist;
import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.entity.UserDetailsServiceImpl;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.response.UserResponseTo;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
    private final UserMapper userMapper;

    public UserResponseTo register(AuthRequestTo authRequestTo) {
        if(userService.findByUsername(authRequestTo.getUsername()) != null) {
            throw new UserAlreadyExist(400L, "User with this username is already exist");
        }

        return userService.save(userMapper.authToEntity(authRequestTo));
    }
}
