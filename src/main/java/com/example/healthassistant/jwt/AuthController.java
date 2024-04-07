package com.example.healthassistant.jwt;

import com.example.healthassistant.jwt.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.DTO.JwtResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class AuthController {
    private final JwtService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtResponseTo AuthenticateAndGetToken(@RequestBody AuthRequestTo authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseTo.builder()
                    .accessToken(service.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
