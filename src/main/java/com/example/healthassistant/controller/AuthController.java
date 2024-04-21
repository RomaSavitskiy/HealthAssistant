package com.example.healthassistant.controller;

import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.DTO.RefreshTokenRequestTo;
import com.example.healthassistant.jwt.service.AuthService;
import com.example.healthassistant.model.response.UserResponseTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public JwtResponseTo register(@RequestBody AuthRequestTo request) {
        return authService.register(request);
    }

    @PostMapping("/activate/{code}")
    public JwtResponseTo activate(@RequestBody AuthRequestTo authRequestTo, @PathVariable String code) {
        return authService.activateUser(authRequestTo, code);
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
