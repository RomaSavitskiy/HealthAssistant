package com.example.healthassistant.auth.jwt;

import com.example.healthassistant.auth.jwt.RefreshToken;
import com.example.healthassistant.auth.jwt.RefreshTokenRepository;
import com.example.healthassistant.user.User;
import com.example.healthassistant.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username).orElseThrow())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000 * 600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    public Optional<RefreshToken> existRefreshTokenByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(user.orElseThrow().getId());
        if (refreshToken.isPresent()) {
            return refreshToken;
        }

        return refreshTokenRepository.findByUserId(user.get().getId());
    }
}
