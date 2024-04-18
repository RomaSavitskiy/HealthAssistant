package com.example.healthassistant.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.jwt.service.JwtService;
import com.example.healthassistant.mapper.WeightMapper;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.model.request.WeightRequestTo;
import com.example.healthassistant.model.response.WeightResponseTo;
import com.example.healthassistant.repository.WeightRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

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

    public Weight update(@Valid Weight weight) throws InvocationTargetException, IllegalAccessException {
        Optional<Weight> oldWeight = weightRepository.findById(weight.getId());
        if (oldWeight.isEmpty()) {
            throw new NotFoundException(404L, "Weight with this id is not found");
        }
        BeanUtils.copyProperties(weight, oldWeight.get());
        return weightRepository.save(weight);
    }
    public void deleteById(@Min(0) Long id) {
        weightRepository.deleteById(id);
    }
}
