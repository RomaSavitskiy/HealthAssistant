package com.example.healthassistant.water;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.auth.jwt.JwtService;
import com.example.healthassistant.user.User;
import com.example.healthassistant.user.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterServiceImpl implements WaterService {
    private final WaterRepository waterRepository;
    private final UserServiceImpl userServiceImpl;
    private final JwtService jwtService;
    private final WaterMapper waterMapper;

    public WaterResponseTo save(@Valid WaterRequestTo waterRequestTo, String token) {
        User user = userServiceImpl.findByUsername(jwtService.extractUsername(token)).orElseThrow();
        return waterMapper.entityToResponse(waterRepository.save(waterMapper.requestToEntity(waterRequestTo, user)));
    }

    public Iterable<WaterResponseTo> findAllForUser(String token) {
        User user = userServiceImpl.findByUsername(jwtService.extractUsername(token)).orElseThrow(
                () -> new NotFoundException(404L, "Water not found"));
        return waterMapper.entityToResponse(waterRepository.findAllByUser(user));
    }

    public WaterResponseTo update(@Valid WaterRequestTo waterRequestTo, String token) {
        User user = userServiceImpl.findByUsername(jwtService.extractUsername(token)).orElseThrow();

        /* BeanUtils.copyProperties(weight, oldWeight.get());*/
        return waterMapper.entityToResponse(waterRepository.save(waterMapper.requestToEntity(waterRequestTo, user)));
    }
    public void deleteById(@Min(0) Long id) {
        waterRepository.deleteById(id);
    }
}
