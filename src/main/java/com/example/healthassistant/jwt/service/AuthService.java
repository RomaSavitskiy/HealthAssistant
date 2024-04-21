package com.example.healthassistant.jwt.service;

import com.example.healthassistant.exceptions.IncorrectCodeException;
import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.jwt.model.DTO.*;
import com.example.healthassistant.jwt.model.entity.EmailConfirmation;
import com.example.healthassistant.jwt.model.entity.RefreshToken;
import com.example.healthassistant.mapper.UserMapper;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.service.EmailService;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<?> verifyResetCode(VerifyResetCodeRequest verifyResetCodeRequest) {
        EmailConfirmation emailConfirmation = emailConfirmationService.
                findLastCodeByLogin(verifyResetCodeRequest.getUsername());

        if (emailConfirmation.getCode().equals(verifyResetCodeRequest.getCode())) {
            return ResponseEntity.ok().body("You can input new password");
        } else {
            throw new IncorrectCodeException(400L, "Incorrect code");
        }
    }

    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        userService.findByUsername(forgotPasswordRequest.getUsername()).orElseThrow(
                () -> new NotFoundException(404L, "User with this email is not found")
        );
        EmailConfirmation emailConfirmation = new EmailConfirmation();
        emailConfirmation.setLogin(forgotPasswordRequest.getUsername());
        emailConfirmation.setCode(randomDigitsService.generateRandomDigits(6));
        emailConfirmationService.save(emailConfirmation);
        sendMail(emailConfirmation.getLogin(), emailConfirmation.getCode());

        return ResponseEntity.ok().body("Сode sent successfully !");
    }

    public void sendMail(String mail, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("Message from Health-Assistant!");
        mailMessage.setText("Hello, " + mail + " . Your code: " + code);
        emailService.sendEmail(mailMessage);
    }

    public JwtResponseTo resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userService.findByUsername(resetPasswordRequest.getUsername()).orElseThrow(
                () -> new NotFoundException(404L, "User with this mail is not found")
        );

        EmailConfirmation emailConfirmation = emailConfirmationService.
                findLastCodeByLogin(resetPasswordRequest.getUsername());

        if (emailConfirmation.getCode().equals(resetPasswordRequest.getCode())) {
            user.setPassword(resetPasswordRequest.getPassword());
            userService.save(userMapper.userToRequest(user));
            AuthRequestTo authRequestTo = new AuthRequestTo();
            authRequestTo.setPassword(resetPasswordRequest.getPassword());
            authRequestTo.setUsername(resetPasswordRequest.getUsername());
            return login(authRequestTo);
        } else {
            throw new NoSuchElementException();
        }
    }
}
