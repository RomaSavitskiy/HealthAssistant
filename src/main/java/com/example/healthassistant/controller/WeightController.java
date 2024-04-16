package com.example.healthassistant.controller;

import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.service.WeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1.0/weight")
@RequiredArgsConstructor
@Tag(name="Weight Controller",
        description="Содержит CRUD операции для сущности Weight")
public class WeightController {
    private final WeightService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weight save(@RequestBody Weight weight) {
        return service.save(weight);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Weight getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Weight> findAllForUserByUserId(@PathVariable Long userId) {
        return service.findAllForUserByUserId(userId);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Weight update(@RequestBody Weight weight) throws InvocationTargetException, IllegalAccessException {
        return service.update(weight);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
