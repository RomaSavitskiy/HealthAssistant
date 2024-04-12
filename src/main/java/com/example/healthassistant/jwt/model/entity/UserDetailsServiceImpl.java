package com.example.healthassistant.jwt.model.entity;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username).orElseThrow();
        if(user == null){
            logger.error("Username not found: " + username);
            throw new NotFoundException(404L, "User not founded");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }
}
