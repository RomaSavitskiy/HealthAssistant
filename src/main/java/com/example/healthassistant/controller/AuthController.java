package com.example.healthassistant.controller;

import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.DTO.RefreshTokenRequestTo;
import com.example.healthassistant.jwt.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/auth")
@RequiredArgsConstructor
@Tag(name="Authentication Controller",
        description="Содержит методы для регистрации и аутентификации пользователя")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseTo AuthenticateAndGetToken(@RequestBody AuthRequestTo authRequestDTO){
       return authService.login(authRequestDTO);
    }

    @PostMapping("/refreshToken")
    public JwtResponseTo getRefreshAndAccessTokensByRefresh(@RequestBody RefreshTokenRequestTo refreshTokenRequestTo){
        return authService.getRefreshAndAccessTokensByRefresh(refreshTokenRequestTo);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestTo request) {
        return authService.register(request);
    }

    @PostMapping("/activate/{code}")
    public JwtResponseTo activate(@RequestBody AuthRequestTo authRequestTo, @PathVariable String code) {
        return authService.activateUser(authRequestTo, code);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(String username) {
        return ResponseEntity.ok().body(authService.forgotPassword(username));
    }
}
