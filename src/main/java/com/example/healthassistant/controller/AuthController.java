package com.example.healthassistant.controller;

import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.DTO.RefreshTokenRequestTo;
import com.example.healthassistant.jwt.model.entity.RefreshToken;
import com.example.healthassistant.jwt.model.service.AuthService;
import com.example.healthassistant.jwt.model.service.JwtService;
import com.example.healthassistant.jwt.model.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Authentication Controller",
        description="Содержит методы для регистрации и аутентификации пользователя")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public JwtResponseTo AuthenticateAndGetToken(@RequestBody AuthRequestTo authRequestDTO){
       return authService.login(authRequestDTO);
    }

    @PostMapping("/refreshToken")
    public JwtResponseTo refreshToken(@RequestBody RefreshTokenRequestTo refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(User -> {
                    String accessToken = jwtService.GenerateToken(User.getUsername());
                    return JwtResponseTo.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestTo request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/ping")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String test() {
        try {
            return "Welcome to ping 2";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
