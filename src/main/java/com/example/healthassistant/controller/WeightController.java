package com.example.healthassistant.controller;

import com.example.healthassistant.jwt.service.JwtService;
import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.model.request.WeightRequestTo;
import com.example.healthassistant.model.response.WeightResponseTo;
import com.example.healthassistant.service.WeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1.0/weight")
@RequiredArgsConstructor
@Tag(name="Weight Controller",
        description="Содержит CRUD операции для сущности Weight")
public class WeightController {
    private final WeightService service;
    private final JwtService jwtService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public WeightResponseTo save(@RequestBody WeightRequestTo weightRequestTo, HttpServletRequest request) {
        return service.save(weightRequestTo, jwtService.getTokenFromHeader(request));
    }

    /*@GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Weight getById(@PathVariable Long id) {
        return service.findById(id);
    }*/

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<WeightResponseTo> findAllForUser(HttpServletRequest request) {
        return service.findAllForUser(jwtService.getTokenFromHeader(request));
    }

   /* @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Weight update(@RequestBody Weight weight) throws InvocationTargetException, IllegalAccessException {
        return service.update(weight);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }*/
}
