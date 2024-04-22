package com.example.healthassistant.water;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.auth.jwt.JwtService;
import com.example.healthassistant.user.User;
import com.example.healthassistant.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterService {
    private final WaterRepository waterRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final WaterMapper waterMapper;

    public WaterResponseTo save(@Valid WaterRequestTo waterRequestTo, String token) {
        User user = userService.findByUsername(jwtService.extractUsername(token)).orElseThrow();
        return waterMapper.entityToResponse(waterRepository.save(waterMapper.requestToEntity(waterRequestTo, user)));
    }

    public Iterable<WaterResponseTo> findAllForUser(String token) {
        User user = userService.findByUsername(jwtService.extractUsername(token)).orElseThrow(
                () -> new NotFoundException(404L, "Water not found"));
        return waterMapper.entityToResponse(waterRepository.findAllByUser(user));
    }
}
