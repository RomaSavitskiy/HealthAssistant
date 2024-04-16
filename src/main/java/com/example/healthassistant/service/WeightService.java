package com.example.healthassistant.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.UserResponseTo;
import com.example.healthassistant.repository.WeightRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightService {
    private final WeightRepository weightRepository;

    public Weight save(@Valid Weight weight) {
        return weightRepository.save(weight);
    }
    public Weight findById(@Min(0) Long id) {
        return weightRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Weight row is not founded"));
    }

    public Iterable<Weight> findAllForUserByUserId(@Min(0) Long userId) {
        return weightRepository.findAllForUserByUserId(userId);
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
