package com.example.healthassistant.water;

import com.example.healthassistant.auth.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/water")
@Tag(name="Water Controller",
        description="Содержит CRUD операции для сущности Water")
public class WaterController {
    private final WaterService service;
    private final JwtService jwtService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public WaterResponseTo save(@RequestBody WaterRequestTo waterRequestTo, HttpServletRequest request) {
        return service.save(waterRequestTo, jwtService.getTokenFromHeader(request));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<WaterResponseTo> findAllForUser(HttpServletRequest request) {
        return service.findAllForUser(jwtService.getTokenFromHeader(request));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public WaterResponseTo update(@RequestBody WaterRequestTo waterRequestTo, HttpServletRequest request) throws IllegalAccessException {
        return service.update(waterRequestTo, jwtService.getTokenFromHeader(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
