package com.example.healthassistant.weight;

import com.example.healthassistant.auth.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<WeightResponseTo> findAllForUser(HttpServletRequest request) {
        return service.findAllForUser(jwtService.getTokenFromHeader(request));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public WeightResponseTo update(@RequestBody WeightRequestTo weightRequestTo, HttpServletRequest request) throws IllegalAccessException {
        return service.update(weightRequestTo, jwtService.getTokenFromHeader(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
