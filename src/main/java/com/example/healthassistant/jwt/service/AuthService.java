package com.example.healthassistant.jwt.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.exceptions.UserAlreadyExist;
import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.DTO.RefreshTokenRequestTo;
import com.example.healthassistant.jwt.model.entity.RefreshToken;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.response.UserResponseTo;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseTo register(AuthRequestTo authRequestTo) {
        if(userService.findByUsername(authRequestTo.getUsername()).isPresent()) {
            throw new UserAlreadyExist(400L, "User with this username is already exist");
        }
        userService.save(userMapper.authToEntity(authRequestTo));
        return null;
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

    public JwtResponseTo getRefreshAndAccessTokensByRefresh(RefreshTokenRequestTo refreshTokenRequestTo) {
        return refreshTokenService.findByToken(refreshTokenRequestTo.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(User -> {
                    String accessToken = jwtService.GenerateToken(User.getUsername());
                    return JwtResponseTo.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestTo.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

    public JwtResponseTo activateUser(AuthRequestTo authRequestTo, String code) {
        User user = userService.findByActivateCode(code).orElseThrow(
                () -> new NotFoundException(404L, "Code is not found"));

        user.setActivationCode(null);

        return login(authRequestTo);
    }
}
