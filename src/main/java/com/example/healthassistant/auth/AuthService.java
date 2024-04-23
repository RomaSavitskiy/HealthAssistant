package com.example.healthassistant.auth;

import com.example.healthassistant.auth.dto.request.*;
import com.example.healthassistant.auth.jwt.JwtResponseTo;
import com.example.healthassistant.auth.mail.MailConfirmationService;
import com.example.healthassistant.exceptions.IncorrectCodeException;
import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.auth.mail.MailConfirmation;
import com.example.healthassistant.auth.jwt.RefreshToken;
import com.example.healthassistant.auth.jwt.JwtService;
import com.example.healthassistant.auth.utils.RandomDigitsService;
import com.example.healthassistant.auth.jwt.RefreshTokenService;
import com.example.healthassistant.user.UserMapper;
import com.example.healthassistant.user.User;
import com.example.healthassistant.auth.mail.MailService;
import com.example.healthassistant.user.UserServiceImpl;
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
	private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final MailConfirmationService mailConfirmationService;
    private final RandomDigitsService randomDigitsService;
    private final MailService mailService;

    public ResponseEntity<?> register(AuthRequestTo authRequestTo) {
        if (userServiceImpl.findByUsername(authRequestTo.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User is already exist");
        }

        MailConfirmation mailConfirmation = new MailConfirmation();
        mailConfirmation.setLogin(authRequestTo.getUsername());
        mailConfirmation.setCode(randomDigitsService.generateRandomDigits(6));
        mailConfirmationService.save(mailConfirmation);
        sendMail(mailConfirmation.getLogin(), mailConfirmation.getCode());
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
        MailConfirmation mailConfirmation = mailConfirmationService.
                findLastCodeByLogin(authRequestTo.getUsername());

        if (mailConfirmation.getCode().equals(code)) {
            userServiceImpl.save(userMapper.authToEntity(authRequestTo));
            return login(authRequestTo);
        } else {
            throw new NoSuchElementException();
        }
    }

    public ResponseEntity<?> verifyResetCode(VerifyResetCodeRequest verifyResetCodeRequest) {
        MailConfirmation mailConfirmation = mailConfirmationService.
                findLastCodeByLogin(verifyResetCodeRequest.getUsername());

        if (mailConfirmation.getCode().equals(verifyResetCodeRequest.getCode())) {
            return ResponseEntity.ok().body("You can input new password");
        } else {
            throw new IncorrectCodeException(400L, "Incorrect code");
        }
    }

    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        userServiceImpl.findByUsername(forgotPasswordRequest.getUsername()).orElseThrow(
                () -> new NotFoundException(404L, "User with this email is not found")
        );
        MailConfirmation mailConfirmation = new MailConfirmation();
        mailConfirmation.setLogin(forgotPasswordRequest.getUsername());
        mailConfirmation.setCode(randomDigitsService.generateRandomDigits(6));
        mailConfirmationService.save(mailConfirmation);
        sendMail(mailConfirmation.getLogin(), mailConfirmation.getCode());

        return ResponseEntity.ok().body("Сode sent successfully !");
    }

    public void sendMail(String mail, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject("Message from Health-Assistant!");
        mailMessage.setText("Hello, " + mail + " . Your code: " + code);
        mailService.sendEmail(mailMessage);
    }

    public JwtResponseTo resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userServiceImpl.findByUsername(resetPasswordRequest.getUsername()).orElseThrow(
                () -> new NotFoundException(404L, "User with this mail is not found")
        );

        MailConfirmation mailConfirmation = mailConfirmationService.
                findLastCodeByLogin(resetPasswordRequest.getUsername());

        if (mailConfirmation.getCode().equals(resetPasswordRequest.getCode())) {
            user.setPassword(resetPasswordRequest.getPassword());
            userServiceImpl.save(userMapper.userToRequest(user));
            AuthRequestTo authRequestTo = new AuthRequestTo();
            authRequestTo.setPassword(resetPasswordRequest.getPassword());
            authRequestTo.setUsername(resetPasswordRequest.getUsername());
            return login(authRequestTo);
        } else {
            throw new NoSuchElementException();
        }
    }
}
