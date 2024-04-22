package com.example.healthassistant.weight;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.auth.jwt.JwtService;
import com.example.healthassistant.user.User;
import com.example.healthassistant.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeightService {
    private final WeightRepository weightRepository;
    private final WeightMapper weightMapper;
    private final JwtService jwtService;
    private final UserService userService;

    public WeightResponseTo save(@Valid WeightRequestTo weightRequestTo, String token) {
        User user = userService.findByUsername(jwtService.extractUsername(token)).orElseThrow();
        return weightMapper.entityToResponse(weightRepository.save(weightMapper.requestToEntity(weightRequestTo, user))
        );
    }
    public Weight findById(@Min(0) Long id) {
        return weightRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Weight row is not founded"));
    }

    public Iterable<WeightResponseTo> findAllForUser(String token) {
        User user = userService.findByUsername(jwtService.extractUsername(token)).orElseThrow();
        return weightMapper.entityToResponse(weightRepository.findAllByUser(user));
    }

    /*public WeightResponseTo update(@Valid Weight weight, String token) throws InvocationTargetException, IllegalAccessException {
        User user = userService.findByUsername(jwtService.extractUsername(token)).orElseThrow();

        BeanUtils.copyProperties(weight, oldWeight.get());
        return weightMapper.entityToResponse(weightRepository.save(weight));
    }*/
    public void deleteById(@Min(0) Long id) {
        weightRepository.deleteById(id);
    }
}
