package com.example.healthassistant.jwt.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.exceptions.UserAlreadyExist;
import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.jwt.model.DTO.JwtResponseTo;
import com.example.healthassistant.jwt.model.DTO.RefreshTokenRequestTo;
import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import com.example.healthassistant.jwt.model.entity.RefreshToken;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.response.UserResponseTo;
import com.example.healthassistant.service.EmailService;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;
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
    private final EmailConfirmationService emailConfirmationService;
    private final RandomDigitsService randomDigitsService;
    private final EmailService emailService;

    public ResponseEntity<?> register(AuthRequestTo authRequestTo) {
        EmailConfirmation emailConfirmation = new EmailConfirmation();
        emailConfirmation.setLogin(authRequestTo.getUsername());
        emailConfirmation.setCode(randomDigitsService.generateRandomDigits(6));
        emailConfirmationService.save(emailConfirmation);
        sendMail(emailConfirmation.getLogin(), emailConfirmation.getCode());
        return ResponseEntity.ok().body("Сode sent successfully !");
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
        EmailConfirmation emailConfirmation = emailConfirmationService.
                findLastCodeByLogin(authRequestTo.getUsername());

        if (emailConfirmation.getCode().equals(code)) {
            userService.save(userMapper.authToEntity(authRequestTo));
            return login(authRequestTo);
        } else {
            throw new NoSuchElementException();
        }
    }

    public JwtResponseTo forgotPassword(String username) {
       /* userService.*/
        return null;
    }

    public void sendMail(String mail, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("Complete Registration!");
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to HealthAssistant, your code: %s",
                mail,
                code
        );
        mailMessage.setText(message);
        emailService.sendEmail(mailMessage);
    }
}
