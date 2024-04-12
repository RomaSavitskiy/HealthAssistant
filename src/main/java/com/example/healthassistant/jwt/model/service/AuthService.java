package com.example.healthassistant.jwt.model.service;

import com.example.healthassistant.exceptions.UserAlreadyExist;
import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.entity.RefreshToken;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public JwtResponseTo register(AuthRequestTo authRequestTo) {
        if(userService.findByUsername(authRequestTo.getUsername()).isPresent()) {
            throw new UserAlreadyExist(400L, "User with this username is already exist");
        }

        userService.save(userMapper.authToEntity(authRequestTo));

       return login(authRequestTo);
    }

    public JwtResponseTo login(AuthRequestTo authRequestTo) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestTo.getUsername(), authRequestTo.getPassword()));
        if(authentication.isAuthenticated()){
            Optional<RefreshToken> existedRefreshToken = refreshTokenService.existRefreshTokenByUsername(authRequestTo.getUsername());
            if (existedRefreshToken.isPresent()) {
                return JwtResponseTo.builder()
                        .accessToken(jwtService.GenerateToken(authRequestTo.getUsername()))
                        .token(existedRefreshToken.get().getToken())
                        .build();
            }

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestTo.getUsername());
            return JwtResponseTo.builder()
                    .accessToken(jwtService.GenerateToken(authRequestTo.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
